package client.dptools;

import client.internal.IModEslApi;
import client.transport.SendMsg;

public class DpTools {

	private final IModEslApi api;

	public DpTools(IModEslApi api) {
		this.api = api;
	}

	public DpTools answer() {
		api.sendMessage(new SendMsg().addCallCommand("answer"));
		return this;
	}

}
