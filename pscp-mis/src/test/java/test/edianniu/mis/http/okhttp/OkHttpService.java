/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月2日 上午11:03:33 
 * @version V1.0
 */
package test.edianniu.mis.http.okhttp;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import com.edianniu.pscp.mis.bean.request.user.GetMsgCodeRequest;
import com.edianniu.pscp.mis.bean.response.user.GetMsgCodeResponse;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月2日 上午11:03:33 
 * @version V1.0
 */
public interface OkHttpService {
	 @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头  
	 @POST("member/getmsgcode")  
	 Call<GetMsgCodeResponse> getMsgCode(@Body GetMsgCodeRequest req);//传入的参数为RequestBody  
}
