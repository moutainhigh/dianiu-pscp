package test.edianniu.mis.http.okhttp;

import okhttp3.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.request.user.GetMsgCodeRequest;
import com.edianniu.pscp.mis.bean.response.user.GetMsgCodeResponse;

public class OkHttpTest {
	private static final Logger log = LoggerFactory.getLogger(OkHttpTest.class);

	public static void main(String[] args) {	
		Retrofit retrofit = new Retrofit.Builder().baseUrl(
				"http://192.168.1.251:5004/").addConverterFactory(FastJsonConverterFactory.create()).build();
		OkHttpService okHttpService = retrofit.create(OkHttpService.class);
		GetMsgCodeRequest req = new GetMsgCodeRequest();
		req.setMobile("13666688420");
		req.setType(0);
		String content = JSONObject.toJSONString(req);
		/*RequestBody body = RequestBody.create(
				okhttp3.MediaType.parse("application/json; charset=utf-8"), content);*/
		Call<GetMsgCodeResponse> call = okHttpService.getMsgCode(req);
		call.enqueue(new Callback<GetMsgCodeResponse>() {
			@Override
			public void onResponse(Call<GetMsgCodeResponse> call,
					Response<GetMsgCodeResponse> response) {
				System.out.println(response.message()+","+response.body());
				//log.debug("success:{}",response.body());
			}
			@Override
			public void onFailure(Call<GetMsgCodeResponse> call, Throwable t) {
				System.out.println("fail:"+t.getMessage());
				log.debug(":{}",t.getMessage());
			}
		});
	}
}
