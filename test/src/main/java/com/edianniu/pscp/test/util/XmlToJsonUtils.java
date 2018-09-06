package com.edianniu.pscp.test.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午2:20:20
 * @version V1.0
 */
public class XmlToJsonUtils {
	/**
	 * 解析Element attr
	 * @param childJSONObject
	 * @param doc
	 * @param parentEl
	 */
	private static void parseAttrJSONObject(JSONObject childJSONObject,
			Document doc, Element parentEl) {
		for (Entry<String, Object> entry : childJSONObject.entrySet()) {
			Attr attr = doc.createAttribute(entry.getKey());
			attr.setNodeValue((String) entry.getValue());
			parentEl.setAttributeNode(attr);
		}
	}
	/**
	 * 递归解析JSONObject
	 * @param childJSONObject
	 * @param doc
	 * @param parentEl
	 */
	private static void parseJSONObject(JSONObject childJSONObject,
			Document doc, Element parentEl) {
		for (Entry<String, Object> entry : childJSONObject.entrySet()) {
			String key = entry.getKey();
			Object obj = entry.getValue();
			if (key.equals("attr")) {
				parseAttrJSONObject((JSONObject) obj, doc, parentEl);
			} else if (key.equals("value")) {
				Text txt = doc.createTextNode((String) obj);
				parentEl.appendChild(txt);
			} else {
				
				if (obj instanceof JSONObject) {
					Element el = doc.createElement(key);
					parseJSONObject((JSONObject) obj, doc, el);
					parentEl.appendChild(el);
				} else if (obj instanceof String) {
					Element el = doc.createElement(key);
					Text txt = doc.createTextNode((String) obj);
					el.appendChild(txt);
					parentEl.appendChild(el);
				}
				else if(obj instanceof List){
					List list=(List) obj;
					for(Object jsonObj:list){
						if(jsonObj instanceof JSONObject){
							Element el = doc.createElement(key);
							parseJSONObject((JSONObject)jsonObj, doc, el);
							parentEl.appendChild(el);
						}
						else{
							Element el = doc.createElement(key);
							Text txt = doc.createTextNode((String) jsonObj);
							el.appendChild(txt);
							parentEl.appendChild(el);
						}
						
					}
					
				}
				
			}

		}
	}
	/**
	 * JSON转为XML
	 * @param json
	 * @return
	 */
	public static String getXmlFromJSON(String json) {
		JSONObject jo = JSON.parseObject(json);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			doc.setXmlStandalone(true);
			Element root = doc.createElement("root");
			doc.appendChild(root);
			for (Entry<String, Object> entry : jo.entrySet()) {
				String key = entry.getKey();
				Object obj = entry.getValue();
				Element el = doc.createElement(key);
				if (obj instanceof JSONObject) {
					parseJSONObject((JSONObject) obj, doc, el);
				} else if (obj instanceof String) {
					Text txt = doc.createTextNode((String) obj);
					el.appendChild(txt);
				}
				else if(obj instanceof List){
					List<JSONObject> list=(List<JSONObject>) obj;
					for(JSONObject jsonObj:list){
						parseJSONObject(jsonObj, doc, el);
					}
					
				}
				root.appendChild(el);
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			/*transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");*/
			transformer.transform(new DOMSource(doc), new StreamResult(
					outputStreamWriter));
			byte[] content=outStream.toByteArray();
		
			return new String(content,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * xml转为JSON
	 * @param xml
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static JSONObject getJSONObjectFromXML(String xml)
			throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream is = getStringStream(xml.trim());
		Document document = builder.parse(is);
		// 获取到document里面的全部结点
		NodeList rootNodes = document.getFirstChild().getChildNodes();

		JSONObject result = new JSONObject();
		parseNodes(rootNodes, result);

		return result;

	}

	/**
	 * 
	 * 判断是否有子节点
	 * 
	 * @param node
	 * @return
	 */
	private static boolean hasChildElementNode(Node node) {
		if (node.getChildNodes() == null) {
			return false;
		}
		if (node.getChildNodes().getLength() == 1) {
			if (node.getFirstChild() instanceof Text) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 递归解析nodes
	 * 
	 * @param nodeList
	 * @param result
	 */
	private static void parseNodes(NodeList nodeList, JSONObject result) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {

				if (hasChildElementNode(node)) {
					JSONObject jo = new JSONObject();
					Object obj=result.get(node.getNodeName());
					if(obj==null){
						if(node.getNodeName().equals("meter")||node.getNodeName().equals("function")){
							List list=new ArrayList();
							list.add(jo);
							result.put(node.getNodeName(), list);
						}
						else{
							result.put(node.getNodeName(), jo);
						}
						
					}
					else{
						if(obj instanceof String||obj instanceof JSONObject){
							List list=new ArrayList();
							list.add(obj);
							list.add(jo);
							result.put(node.getNodeName(), list);
						}
						else if(obj instanceof List){
							List list=(List) obj;
							list.add(jo);
						}
					}
					
					if (node.hasAttributes()) {
						NamedNodeMap namedNodeMap = node.getAttributes();
						JSONObject attr = new JSONObject();
						jo.put("attr", attr);
						for (int j = 0; j < namedNodeMap.getLength(); j++) {
							Node nnode = namedNodeMap.item(j);
							attr.put(nnode.getNodeName(), nnode.getNodeValue());
						}
					}
					parseNodes(node.getChildNodes(), jo);
				} else {
					if (node.hasAttributes()) {
						JSONObject jo = new JSONObject();
						NamedNodeMap namedNodeMap = node.getAttributes();
						JSONObject attr = new JSONObject();
						jo.put("attr", attr);
						for (int j = 0; j < namedNodeMap.getLength(); j++) {
							Node nnode = namedNodeMap.item(j);
							attr.put(nnode.getNodeName(), nnode.getNodeValue());
						}
						jo.put("value", node.getTextContent());
						Object obj=result.get(node.getNodeName());
						if(obj==null){
							if(node.getNodeName().equals("meter")||node.getNodeName().equals("function")){
								List list=new ArrayList();
								list.add(jo);
								result.put(node.getNodeName(), list);
							}
							else{
								result.put(node.getNodeName(), jo);
							}
						}
						else{
							if(obj instanceof String||obj instanceof JSONObject){
								List list=new ArrayList();
								list.add(obj);
								list.add(jo);
								result.put(node.getNodeName(), list);
							}
							else if(obj instanceof List){
								List list=(List) obj;
								list.add(jo);
							}
						}
						
					} else {
						Object obj=result.get(node.getNodeName());
						if(obj==null){
							result.put(node.getNodeName(), node.getTextContent());
						}
						else{
							if(obj instanceof String||obj instanceof JSONObject){
								List list=new ArrayList();
								list.add(obj);
								list.add(node.getTextContent());
								result.put(node.getNodeName(), list);
							}
							else if(obj instanceof List){
								List list=(List) obj;
								list.add(node.getTextContent());
							}
						}
						
					}

				}
			}

		}

	}

	public static InputStream getStringStream(String input) {
		ByteArrayInputStream inputStringStream = null;
		if (input != null && !input.trim().equals("")) {
			inputStringStream = new ByteArrayInputStream(
					input.getBytes());
		}
		return inputStringStream;
	}

	public static void main(String args[]) throws ParserConfigurationException,
			IOException, SAXException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "<root>"
				+ "<common>" + "<building_id>330100A088</building_id>"
				+ "<gateway_id>01</gateway_id>" + "<type>request</type>"
				+ "</common>" + "<id_validate operation=\"request\">"
				+ "</id_validate>" + "</root>";
		String xml2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "<root>"
				+ "<common>" + "<building_id>330100A088</building_id>"
				+ "<gateway_id>01</gateway_id>" + "<type>sequence</type>"
				+ "</common>" + "<id_validate operation=\"sequence\">"
				+ "<sequence>00787278</sequence>" + "</id_validate>"
				+ "</root>";
		String xml3 = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
				+ "<root>"
				+ "<common>"
				+ "<building_id>330100A088</building_id>"
				+ "<gateway_id>01</gateway_id>"
			/*	+ "<gateway_id>02</gateway_id>"*/
			/*	+ "<type id=\"1\">report1</type>"
				+ "<type id=\"2\">report2</type>"*/
				+ "<type>report</type>"
				+ "</common>"
				+ "<data operation=\"report\">"
				+ "<sequence>00787278</sequence>"
				+ "<parser>yes</parser>"
				+ "<time>20130307150109</time>"
				+ "<meter id=\"1\" addr=\"199271\" tp=\"1\" name=\"三楼电梯1\">"
				+ "<function id=\"1\" coding=\"01A00\" error=\"192\">5.200</function>"
				/*+ "<function id=\"2\" coding=\"01A00\" error=\"192\">5.200</function>"
				+ "<function id=\"3\" coding=\"01A00\" error=\"192\">5.200</function>"
				+ "<function id=\"4\" coding=\"01A00\" error=\"192\">5.200</function>"*/
				+ "</meter>"
			/*	+ "<meter id=\"2\" addr=\"199272\" tp=\"1\" name=\"三楼电梯2\">"
				+ "<function id=\"1\" coding=\"01A00\" error=\"192\">5.200</function>"
				+ "<function id=\"2\" coding=\"01A00\" error=\"192\">5.200</function>"
				+ "<function id=\"3\" coding=\"01A00\" error=\"192\">5.200</function>"
				+ "<function id=\"4\" coding=\"01A00\" error=\"192\">5.200</function>"
				+ "</meter>"*/
				+ "</data>" + "</root>";
       
		JSONObject result = XmlToJsonUtils.getJSONObjectFromXML(xml3);
		System.out.println(result.toJSONString());
		String xmlResult = getXmlFromJSON(result.toJSONString());
		
		System.out.println(xmlResult);
	}

}
