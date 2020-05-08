package client.outbound;

import client.inbound.IEslEventListener;
import client.internal.Context;
import client.transport.event.EslEvent;

public interface IClientHandler extends IEslEventListener {
	void onConnect(Context ctx, EslEvent event);
}
