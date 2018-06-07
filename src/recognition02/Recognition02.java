package recognition02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

public class Recognition02 {

	public static void main(String[] args){

		VisualRecognition service = new VisualRecognition("2018-03-19");
		service.setApiKey("49d9fa9ea3694ffdcb2ec3a9812f7976a5e6d904");

		MySQL mysql = new MySQL();
		InputStream imagesStream = null;
		try {
			imagesStream = new FileInputStream("./img/fruitbowl.jpg");
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
		  .imagesFile(imagesStream)
		  .imagesFilename("fruitbowl.jpg")
		  .threshold((float) 0.6)
		  .owners(Arrays.asList("IBM"))
		  .build();
		ClassifiedImages result = service.classify(classifyOptions).execute();
		System.out.println(result);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node=null;
		try {
			 node = mapper.readTree(result.toString());
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		String class1=node.get("images").get(0).get("classifiers").get(0).get("classes").get(0).get("class").toString();
		Double score1 = node.get("images").get(0).get("classifiers").get(0).get("classes").get(0).get("score").asDouble();
		String class2=node.get("images").get(0).get("classifiers").get(0).get("classes").get(1).get("class").toString();
		Double score2 = node.get("images").get(0).get("classifiers").get(0).get("classes").get(1).get("score").asDouble();
		String class3=node.get("images").get(0).get("classifiers").get(0).get("classes").get(2).get("class").toString();
		Double score3 = node.get("images").get(0).get("classifiers").get(0).get("classes").get(2).get("score").asDouble();


		System.out.println(class1);
		System.out.println(score1);
		System.out.println(class2);
		System.out.println(score2);
		System.out.println(class3);
		System.out.println(score3);
		mysql.updateImage(class1,score1,class2,score2,class3,score3);



	}

}
