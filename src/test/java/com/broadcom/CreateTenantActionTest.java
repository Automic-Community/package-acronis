package com.broadcom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateTenantActionTest {
	
	@Test
	public void CreateTenantAction() {
		DemoAction action = new DemoAction();
		action.yourname = "Michael"; 
		action.run();
		Assertions.assertEquals("Hello Michael", action.greeting);
	} 

}
