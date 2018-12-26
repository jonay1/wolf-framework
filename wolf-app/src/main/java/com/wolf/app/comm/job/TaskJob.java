package com.wolf.app.comm.job;

import java.util.Map;

public interface TaskJob {

	void execute(Map<String, Object> context);
}
