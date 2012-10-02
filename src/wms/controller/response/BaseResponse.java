package wms.controller.response;

abstract public class BaseResponse {
	Long time;
	String handler;

	public BaseResponse(Long time, String handler) {
		super();
		this.time = time;
		this.handler = handler;
	}

}
