package com.broadcom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateTenantActionTest {


	@Test
	public void CreateTenantAction() {
		CreateTenantAction action = new CreateTenantAction();
//		action.parentTenantId = ""; 
		action.run();
		Assertions.assertEquals("", action.tenantId);
	} 

}
