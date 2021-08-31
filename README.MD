## Getting Started:


###### Description

Acronis Cyber Cloud is a cloud platform that enables service providers, re-sellers, and distributors to deliver data protection services to their partners and customers.The services are provided at the partner level, down to the customer company level and the end-user level. The services management is available through web applications called the service consoles. The tenant and user account management is available through a web application called the management portal.
The management portal enables administrators to:
* Monitor the service usage and access the service consoles
* Manage tenants
* Manage user accounts
* Configure services and quotas for tenants
* Manage storage
* Manage branding
* Generate reports about the service usage


###### Actions
 
 1.  Activate User
 2.  Create Client
 3.  Create Tenant
 4.  Create User
 5.  Delete Tenant
 6.  Delete User
 7.  Disable Tenant
 8.  Disable User
 9.  Enable Tenant
 10. Enable User
 11. Get Tenant
 12. Get Token
 13. Get User
 14. Get User Self
 15. Switch Customer To Production
 16. Update Tenant
 17. Update User
 18. Update User Role

 
 ###### Compatibility:

1. Acronis REST API v2
2. OpenJDK 1.8 or higher must be installed on Agent OS and "java" must be set as executable on the same.

###### Prerequisite:

1. Automation Engine should be installed.
2. ITPA Shared Action Pack should be installed.
3. Maven

###### Steps to install action pack source code:

1. Clone the code to your machine.
2. Go to the package directory.
3. Run the maven command 'mvn clean package' inside the directory containing the pom.xml file.(source/)
4. After executing the step 3 it will create zip under the target folder. Upload the PCK.AUTOMIC_ACRONIS-1.0.0.zip 
   into the Automation Engine by going to the Administration tab and clicking the Packs section and In the Packs section clicking the Install From File and uploading the zip file there.

###### Useful References

1. [About Packs and Plug-ins](https://docs.automic.com/documentation/webhelp/english/AA/12.3/DOCU/12.3/Automic%20Automation%20Guides/help.htm#PluginManager/PM_AboutPacksandPlugins.htm?Highlight=Action%20packs)
2. [Working with Packs and Plug-ins](https://docs.automic.com/documentation/webhelp/english/AA/12.3/DOCU/12.3/Automic%20Automation%20Guides/help.htm#PluginManager/PM_WorkingWith.htm#link10)
3. [Actions and Action Packs](https://docs.automic.com/documentation/webhelp/english/AA/12.3/DOCU/12.3/Automic%20Automation%20Guides/help.htm#_Common/ReleaseHighlights/RH_Plugin_PackageManager.htm?Highlight=Action%20packs)
4. [PACKS Compatibility Mode](https://docs.automic.com/documentation/webhelp/english/AA/12.3/DOCU/12.3/Automic%20Automation%20Guides/help.htm#AWA/Variables/UC_CLIENT_SETTINGS/UC_CLIENT_PACKS_COMPATIBILITY_MODE.htm?Highlight=Action%20packs)
5. [Working with actions](https://docs.automic.com/documentation/webhelp/english/AA/12.3/DOCU/12.3/Automic%20Automation%20Guides/help.htm#ActionBuilder/AB_WorkingWith.htm#link4)
6. [Installing and Configuring the Action Builder](https://docs.automic.com/documentation/webhelp/english/AA/12.3/DOCU/12.3/Automic%20Automation%20Guides/help.htm#ActionBuilder/install_configure_plugins_AB.htm?Highlight=Action%20packs)
			
###### Copyright and License: 

Broadcom does not support, maintain or warrant Solutions, Templates, Actions and any other content published on the Community and is subject to Broadcom Community [Terms and Conditions](https://community.broadcom.com/termsandconditions)

 
