package com.serverless;

import java.text.SimpleDateFormat;
import java.util.*;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private DynamoDB dynamoDb;
	private AmazonDynamoDB client;
	private String DYNAMODB_TABLE_NAME = "highscores_table";

	private static final Logger LOG = LogManager.getLogger(Handler.class);
	static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.info("Incoming Request: {}", input);

		initDynamoDbClient();
		if (input.get("httpMethod").toString().equals("POST")) {
			return processPost(input);
		} else {
			return processGet();
		}
	}

	private ApiGatewayResponse processGet() {
		ScanRequest scanRequest = new ScanRequest().withTableName(DYNAMODB_TABLE_NAME);

		ScanResult result = client.scan(scanRequest);
		Map<String, Object> results = new HashMap<>();
		for (Map<String, AttributeValue> s : result.getItems()){
			Map<String, Object> singleResult = new HashMap<>();
			singleResult.put("playerOne", s.get("playerOne").getS());
			singleResult.put("playerTwo", s.get("playerTwo").getS());
			singleResult.put("playerOneScore", s.get("playerOneScore").getN());
			singleResult.put("playerTwoScore", s.get("playerTwoScore").getN());
			singleResult.put("date", s.get("date").getS());
			results.put(s.get("id").getS(), singleResult);
		}

		Response responseBody = new Response("GET Highscores list", results);
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.build();
	}

	private ApiGatewayResponse processPost(Map<String, Object> input) {
		int statusCode = 200;
		try {
			Map<String, Object> response = new ObjectMapper().readValue(input.get("body").toString(), HashMap.class);
			Table table = dynamoDb.getTable(DYNAMODB_TABLE_NAME);
			Item item = new Item()
					.withPrimaryKey("id", UUID.randomUUID().toString())
					.withString("playerOne", (String)response.get("playerOne"))
					.withString("playerTwo", (String)response.get("playerTwo"))
					.withNumber("playerOneScore", (Integer)response.get("playerOneScore"))
					.withNumber("playerTwoScore", (Integer)response.get("playerTwoScore"))
					.withString("date", dateFormatter.format(new Date()));

			table.putItem(item);
		} catch (Exception e) {
			LOG.error("Body not JSON: {}", e.getMessage());
			statusCode = 500;
		}

		return ApiGatewayResponse.builder()
				.setStatusCode(statusCode)
				.build();
	}

	private void initDynamoDbClient() {
		client = AmazonDynamoDBClientBuilder
						.standard()
						.withEndpointConfiguration(
								new AwsClientBuilder
										.EndpointConfiguration("https://dynamodb.eu-central-1.amazonaws.com", "eu-central-1"))
						.build();
		dynamoDb = new DynamoDB(client);
	}
}
