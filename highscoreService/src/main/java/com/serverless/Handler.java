package com.serverless;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private AmazonDynamoDBClient dynamoDb;
	private String DYNAMODB_TABLE_NAME = "highscores_table";

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: {}", input);
		initDynamoDbClient();
		Response responseBody = new Response("Go Serverless v1.x! Your function executed successfully!", input);
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
	}

	private void initDynamoDbClient() {
		DynamoDB dynamoDB =new DynamoDB(AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://dynamodb.eu-central-1.amazonaws.com", "eu-central-1")).build());

		Table table = dynamoDB.getTable(DYNAMODB_TABLE_NAME);
		Map<String, Object> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":pr", 100);

		ItemCollection<ScanOutcome> items = table.scan(
				"Price < :pr", //FilterExpression
				"Id, Title, ProductCategory, Price", //ProjectionExpression
				null, //ExpressionAttributeNames - not used in this example
				expressionAttributeValues);

		System.out.println("Scan of for items with a price less than 100.");
		Iterator<Item> iterator = items.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().toJSONPretty());
		}
	}
}
