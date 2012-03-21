package org.rubychina.android.api.request;

import java.util.HashMap;
import java.util.Map;

import org.rubychina.android.activity.TopicsActivity;
import org.rubychina.android.api.RCAPIContext;
import org.rubychina.android.api.response.ActiveTopicsResponse;

import yek.cache.Cache;

public class ActiveTopicsRequest extends RCAPIGet<ActiveTopicsResponse> {

	private static final String TAG = "HotTopicsRequest";
	private static final String url = "api/topics.json";
	
	private static final int DEFAULT_SIZE = 30;
	
	private static final String SIZE_KEY = "size";
	
	private int size = DEFAULT_SIZE;
	private int nodeId = TopicsActivity.HOT_TOPICS_NODE_ID;
	
	public void setSize(int size) {
		if(size < 0 || size > 100) {
			this.size = DEFAULT_SIZE;
		} else {
			this.size = size; 
		}
	}
	
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	
	@Override
	public String getRequestURL(RCAPIContext context) {
		if(nodeId == TopicsActivity.HOT_TOPICS_NODE_ID) {
			return context.getServer() + url;
		} else {
			return context.getServer() + "api/topics/node/" + nodeId + ".json";
		}
	}

	@Override
	public Class<ActiveTopicsResponse> getResponseClass() {
		return ActiveTopicsResponse.class;
	}

	@Override
	public Map<String, String> getTextParams(RCAPIContext context) {
		//TODO the size seems do not work
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(SIZE_KEY, size + "");
		return params;
	}

	@Override
	public String getCacheRelativePathOrURL() {
		//NOTE always ensure providing a unique cache for a request
		return makeCachePath("api", "topics", "list", "default", size + "");
	}

	@Override
	public long getCacheTime() {
		return Cache.EXPIRED;
	}

}