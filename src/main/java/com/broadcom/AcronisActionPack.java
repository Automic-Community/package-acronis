package com.broadcom;

import com.broadcom.apdk.api.BaseActionPack;
import com.broadcom.apdk.api.annotations.ActionPack;

@ActionPack(name = "PCK.AUTOMIC_ACRONIS", title = "Acronis action pack", category = "Acronis Actions", company = "Broadcom", homepage = "http://www.broadcom.com")
public class AcronisActionPack extends BaseActionPack {
	
	public AcronisActionPack() {
		setDocumentation("About the Package\r\n" + 
				"================================\r\n" + 
				"Acronis Cyber Cloud is a cloud platform that enables service providers, re-sellers, and distributors to deliver data protection services to their partners and customers.\r\n" + 
				"\r\n" + 
				"The services are provided at the partner level, down to the customer company level and the end-user level.\r\n" + 
				"\r\n" + 
				"The services management is available through web applications called the service consoles. The tenant and user account management is available through a web application called the management portal.\r\n" + 
				"\r\n" + 
				"The management portal enables administrators to:\r\n" + 
				"\r\n" + 
				"Monitor the service usage and access the service consoles\r\n" + 
				"Manage tenants\r\n" + 
				"Manage user accounts\r\n" + 
				"Configure services and quotas for tenants\r\n" + 
				"Manage storage\r\n" + 
				"Manage branding\r\n" + 
				"Generate reports about the service usage\r\n" + 
				"\r\n" + 
				"=Supported versions=\r\n" + 
				"Acronis REST API v2\r\n" + 
				"\r\n" + 
				"=Setup=  \r\n" + 
				"* OpenJDK 1.8 or higher must be installed on Agent OS and \"java\" must be set as executable on the same.\r\n" + 
				"* The Agent should be able to access Acronis URL in order to make HTTP requests.\r\n" + 
				" \r\n" + 
				"=Common Inputs=\r\n" + 
				"* Acronis Endpoint URL			: Endpoint to connect to the acronis. E.g. https://us5-cloud.acronis.com\r\n" + 
				"* API Version					: Acronis REST API version. E.g. 2\r\n" + 
				"\r\n" + 
				"* Username 						: Username to access\r\n" + 
				"* Password						: Password for username\r\n" + 
				"Note: GET_TOKEN and ACTIVATE_USER action needs Client Id and Client Secret for the authentication instead of Username and Password. CREATE_CLIENT action can be used to get the client id and client secret.\r\n" + 
				"* Client ID            		    : Client ID \r\n" + 
				"* Client Secret                 : Client Secret \r\n" + 
				"	\r\n" + 
				"* Overwrite Agent				: Overwrites the default agent\r\n" + 
				"* Overwrite Login				: Overwrites the default login\r\n" + 
				"\r\n" + 
				"=Actions=\r\n" + 
				"Following actions are supported.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.CREATE_TENANT\r\n" + 
				"* Action Description: This action creates a tenant.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.UPDATE_TENANT\r\n" + 
				"* Action Description: This action updates a tenant.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.DELETE_TENANT\r\n" + 
				"* Action Description: This action deletes a tenant.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.CREATE_CLIENT\r\n" + 
				"* Action Description: This action will Create a new Client.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.GET_TOKEN\r\n" + 
				"* Action Description: This action will get the token using client id and secret.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.ENABLE_TENANT\r\n" + 
				"* Action Description: This action will enable a tenant.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.DISABLE_TENANT\r\n" + 
				"* Action Description: This action will disable a tenant.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.DELETE_USER\r\n" + 
				"* Action Description: This action will delete a user.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.GET_USER_SELF\r\n" + 
				"* Action Description: This action will get the loggedin user details from Acronis.\r\n" +
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.GET_USER\r\n" + 
				"* Action Description: This action will get the user details from Acronis.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.GET_TENANT\r\n" + 
				"* Action Description: This action gets the details of a tenant.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.CREATE_USER\r\n" + 
				"* Action Description: This action used to create a User.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.UPDATE_USER\r\n" + 
				"* Action Description: This action used to update a user.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.ENABLE_USER\r\n" + 
				"* Action Description: This action used to enable the user.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.DISABLE_USER\r\n" + 
				"* Action Description: This action used to disable the user.\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.ACTIVATE_USER\r\n" + 
				"* Action Description: This action will activate a user.\r\n" + 
				"\r\n" +
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.SWITCH_CUSTOMER_TO_PRODUCTION\r\n" + 
				"* Action Description: This action will switch customer from trial to production mode .\r\n" + 
				"\r\n" + 
				"* Action name: PCK.AUTOMIC_ACRONIS.PUB.ACTION.UPDATE_USER_ROLE\r\n" + 
				"* Action Description: This action will update the role of the user.");
		setLicenses("Licenses used\r\n" + 
				"\r\n" + 
				"=================\r\n" + 
				"=   Libraries   =\r\n" + 
				"=================\r\n" + 
				"\r\n" + 
				"jersey-client                  version 1.19.4  CDDL - Version 1.1             https://jersey.java.net/license.html\r\n" + 
				"javax.json(org.glassfish)      version 1.0.4   CDDL - Version 1.1             https://jsonp.java.net/license.html\r\n" + 
				"jersey-core                    version 1.19.4  CDDL - Version 1.1             https://jersey.java.net/license.html\r\n" + 
				"commons-lang3                  version 3.8.1   APACHE LICENSE, VERSION 2.0    http://commons.apache.org/proper/commons-lang/download_lang.cgi\r\n" + 
				"commons-cli                    version 1.4     APACHE LICENSE, VERSION 2.0    http://commons.apache.org/proper/commons-cli/download_cli.cgi\r\n" + 
				"genson                         version 0.99    APACHE LICENSE, VERSION 2.0    http://genson.io/\r\n" + 
				"jsr311-api                     version 1.1.1   CDDL - Version 1.0\r\n" + 
				"\r\n" + 
				"================== \r\n" + 
				"=    Licenses    =	\r\n" + 
				"==================\r\n" + 
				"\r\n" + 
				"COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0) \r\n" + 
				"=====================================\r\n" + 
				"\r\n" + 
				"1. Definitions.\r\n" + 
				"1.1. Contributor means each individual or entity that creates or contributes to the creation of Modifications.\r\n" + 
				"1.2. Contributor Version means the combination of the Original Software, prior Modifications used by a Contributor (if any), and the Modifications made by that particular Contributor.\r\n" + 
				"1.3. Covered Software means (a) the Original Software, or (b) Modifications, or (c) the combination of files containing Original Software with files containing Modifications, in each case including portions thereof.\r\n" + 
				"1.4. Executable means the Covered Software in any form other than Source Code.\r\n" + 
				"1.5. Initial Developer means the individual or entity that first makes Original Software available under this License.\r\n" + 
				"1.6. Larger Work means a work which combines Covered Software or portions thereof with code not governed by the terms of this License.\r\n" + 
				"1.7. License means this document.\r\n" + 
				"1.8. Licensable means having the right to grant, to the maximum extent possible, whether at the time of the initial grant or subsequently acquired, any and all of the rights conveyed herein.\r\n" + 
				"1.9. Modifications means the Source Code and Executable form of any of the following:\r\n" + 
				"A. Any file that results from an addition to, deletion from or modification of the contents of a file containing Original Software or previous Modifications;\r\n" + 
				"B. Any new file that contains any part of the Original Software or previous Modification; orC. Any new file that is contributed or otherwise made available under the terms of this License.\r\n" + 
				"1.10. Original Software means the Source Code and Executable form of computer software code that is originally released under this License.\r\n" + 
				"1.11. Patent Claims means any patent claim(s), now owned or hereafter acquired, including without limitation, method, process, and apparatus claims, in any patent Licensable by grantor.\r\n" + 
				"1.12. Source Code means (a) the common form of computer software code in which modifications are made and (b) associated documentation included in or with such code.\r\n" + 
				"1.13. You (or Your) means an individual or a legal entity exercising rights under, and complying with all of the terms of, this License. For legal entities, You includes any entity which controls, is controlled by, or is under common control with You. For purposes of this definition, control means (a) the power, direct or indirect, to cause the direction or management of such entity, whether by contract or otherwise, or (b) ownership of more than fifty percent (50%) of the outstanding shares or beneficial ownership of such entity.\r\n" + 
				"2. License Grants.\r\n" + 
				"2.1. The Initial Developer Grant.\r\n" + 
				"Conditioned upon Your compliance with Section 3.1 below and subject to third party intellectual property claims, the Initial Developer hereby grants You a world-wide, royalty-free, non-exclusive license:\r\n" + 
				"(a) under intellectual property rights (other than patent or trademark) Licensable by Initial Developer, to use, reproduce, modify, display, perform, sublicense and distribute the Original Software (or portions thereof), with or without Modifications, and/or as part of a Larger Work; and(b) under Patent Claims infringed by the making, using or selling of Original Software, to make, have made, use, practice, sell, and offer for sale, and/or otherwise dispose of the Original Software (or portions thereof).(c) The licenses granted in Sections 2.1(a) and (b) are effective on the date Initial Developer first distributes or otherwise makes the Original Software available to a third party under the terms of this License.(d) Notwithstanding Section 2.1(b) above, no patent license is granted: (1) for code that You delete from the Original Software, or (2) for infringements caused by: (i) the modification of the Original Software, or (ii) the combination of the Original Software with other software or devices.\r\n" + 
				"2.2. Contributor Grant.\r\n" + 
				"Conditioned upon Your compliance with Section 3.1 below and subject to third party intellectual property claims, each Contributor hereby grants You a world-wide, royalty-free, non-exclusive license:\r\n" + 
				"(a) under intellectual property rights (other than patent or trademark) Licensable by Contributor to use, reproduce, modify, display, perform, sublicense and distribute the Modifications created by such Contributor (or portions thereof), either on an unmodified basis, with other Modifications, as Covered Software and/or as part of a Larger Work; and(b) under Patent Claims infringed by the making, using, or selling of Modifications made by that Contributor either alone and/or in combination with its Contributor Version (or portions of such combination), to make, use, sell, offer for sale, have made, and/or otherwise dispose of: (1) Modifications made by that Contributor (or portions thereof); and (2) the combination of Modifications made by that Contributor with its Contributor Version (or portions of such combination).(c) The licenses granted in Sections 2.2(a) and 2.2(b) are effective on the date Contributor first distributes or otherwise makes the Modifications available to a third party.(d) Notwithstanding Section 2.2(b) above, no patent license is granted: (1) for any code that Contributor has deleted from the Contributor Version; (2) for infringements caused by: (i) third party modifications of Contributor Version, or (ii) the combination of Modifications made by that Contributor with other software (except as part of the Contributor Version) or other devices; or (3) under Patent Claims infringed by Covered Software in the absence of Modifications made by that Contributor.\r\n" + 
				"3. Distribution Obligations.\r\n" + 
				"3.1. Availability of Source Code.\r\n" + 
				"Any Covered Software that You distribute or otherwise make available in Executable form must also be made available in Source Code form and that Source Code form must be distributed only under the terms of this License. You must include a copy of this License with every copy of the Source Code form of the Covered Software You distribute or otherwise make available. You must inform recipients of any such Covered Software in Executable form as to how they can obtain such Covered Software in Source Code form in a reasonable manner on or through a medium customarily used for software exchange.\r\n" + 
				"3.2. Modifications.\r\n" + 
				"The Modifications that You create or to which You contribute are governed by the terms of this License. You represent that You believe Your Modifications are Your original creation(s) and/or You have sufficient rights to grant the rights conveyed by this License.\r\n" + 
				"3.3. Required Notices.\r\n" + 
				"You must include a notice in each of Your Modifications that identifies You as the Contributor of the Modification. You may not remove or alter any copyright, patent or trademark notices contained within the Covered Software, or any notices of licensing or any descriptive text giving attribution to any Contributor or the Initial Developer.\r\n" + 
				"3.4. Application of Additional Terms.\r\n" + 
				"You may not offer or impose any terms on any Covered Software in Source Code form that alters or restricts the applicable version of this License or the recipients rights hereunder. You may choose to offer, and to charge a fee for, warranty, support, indemnity or liability obligations to one or more recipients of Covered Software. However, you may do so only on Your own behalf, and not on behalf of the Initial Developer or any Contributor. You must make it absolutely clear that any such warranty, support, indemnity or liability obligation is offered by You alone, and You hereby agree to indemnify the Initial Developer and every Contributor for any liability incurred by the Initial Developer or such Contributor as a result of warranty, support, indemnity or liability terms You offer.\r\n" + 
				"3.5. Distribution of Executable Versions.\r\n" + 
				"You may distribute the Executable form of the Covered Software under the terms of this License or under the terms of a license of Your choice, which may contain terms different from this License, provided that You are in compliance with the terms of this License and that the license for the Executable form does not attempt to limit or alter the recipients rights in the Source Code form from the rights set forth in this License. If You distribute the Covered Software in Executable form under a different license, You must make it absolutely clear that any terms which differ from this License are offered by You alone, not by the Initial Developer or Contributor. You hereby agree to indemnify the Initial Developer and every Contributor for any liability incurred by the Initial Developer or such Contributor as a result of any such terms You offer.\r\n" + 
				"3.6. Larger Works.\r\n" + 
				"You may create a Larger Work by combining Covered Software with other code not governed by the terms of this License and distribute the Larger Work as a single product. In such a case, You must make sure the requirements of this License are fulfilled for the Covered Software.4. Versions of the License.\r\n" + 
				"4.1. New Versions.\r\n" + 
				"Sun Microsystems, Inc. is the initial license steward and may publish revised and/or new versions of this License from time to time. Each version will be given a distinguishing version number. Except as provided in Section 4.3, no one other than the license steward has the right to modify this License.\r\n" + 
				"4.2. Effect of New Versions.\r\n" + 
				"You may always continue to use, distribute or otherwise make the Covered Software available under the terms of the version of the License under which You originally received the Covered Software. If the Initial Developer includes a notice in the Original Software prohibiting it from being distributed or otherwise made available under any subsequent version of the License, You must distribute and make the Covered Software available under the terms of the version of the License under which You originally received the Covered Software. Otherwise, You may also choose to use, distribute or otherwise make the Covered Software available under the terms of any subsequent version of the License published by the license steward.\r\n" + 
				"4.3. Modified Versions.\r\n" + 
				"When You are an Initial Developer and You want to create a new license for Your Original Software, You may create and use a modified version of this License if You: (a) rename the license and remove any references to the name of the license steward (except to note that the license differs from this License); and (b) otherwise make it clear that the license contains terms which differ from this License.5. DISCLAIMER OF WARRANTY.\r\n" + 
				"COVERED SOFTWARE IS PROVIDED UNDER THIS LICENSE ON AN AS IS BASIS, WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT LIMITATION, WARRANTIES THAT THE COVERED SOFTWARE IS FREE OF DEFECTS, MERCHANTABLE, FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE COVERED SOFTWARE IS WITH YOU. SHOULD ANY COVERED SOFTWARE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR CORRECTION. THIS DISCLAIMER OF WARRANTY CONSTITUTES AN ESSENTIAL PART OF THIS LICENSE. NO USE OF ANY COVERED SOFTWARE IS AUTHORIZED HEREUNDER EXCEPT UNDER THIS DISCLAIMER.6. TERMINATION.\r\n" + 
				"6.1. This License and the rights granted hereunder will terminate automatically if You fail to comply with terms herein and fail to cure such breach within 30 days of becoming aware of the breach. Provisions which, by their nature, must remain in effect beyond the termination of this License shall survive.\r\n" + 
				"6.2. If You assert a patent infringement claim (excluding declaratory judgment actions) against Initial Developer or a Contributor (the Initial Developer or Contributor against whom You assert such claim is referred to as Participant) alleging that the Participant Software (meaning the Contributor Version where the Participant is a Contributor or the Original Software where the Participant is the Initial Developer) directly or indirectly infringes any patent, then any and all rights granted directly or indirectly to You by such Participant, the Initial Developer (if the Initial Developer is not the Participant) and all Contributors under Sections 2.1 and/or 2.2 of this License shall, upon 60 days notice from Participant terminate prospectively and automatically at the expiration of such 60 day notice period, unless if within such 60 day period You withdraw Your claim with respect to the Participant Software against such Participant either unilaterally or pursuant to a written agreement with Participant.\r\n" + 
				"6.3. In the event of termination under Sections 6.1 or 6.2 above, all end user licenses that have been validly granted by You or any distributor hereunder prior to termination (excluding licenses granted to You by any distributor) shall survive termination.7. LIMITATION OF LIABILITY.\r\n" + 
				"UNDER NO CIRCUMSTANCES AND UNDER NO LEGAL THEORY, WHETHER TORT (INCLUDING NEGLIGENCE), CONTRACT, OR OTHERWISE, SHALL YOU, THE INITIAL DEVELOPER, ANY OTHER CONTRIBUTOR, OR ANY DISTRIBUTOR OF COVERED SOFTWARE, OR ANY SUPPLIER OF ANY OF SUCH PARTIES, BE LIABLE TO ANY PERSON FOR ANY INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES OF ANY CHARACTER INCLUDING, WITHOUT LIMITATION, DAMAGES FOR LOST PROFITS, LOSS OF GOODWILL, WORK STOPPAGE, COMPUTER FAILURE OR MALFUNCTION, OR ANY AND ALL OTHER COMMERCIAL DAMAGES OR LOSSES, EVEN IF SUCH PARTY SHALL HAVE BEEN INFORMED OF THE POSSIBILITY OF SUCH DAMAGES. THIS LIMITATION OF LIABILITY SHALL NOT APPLY TO LIABILITY FOR DEATH OR PERSONAL INJURY RESULTING FROM SUCH PARTYS NEGLIGENCE TO THE EXTENT APPLICABLE LAW PROHIBITS SUCH LIMITATION. SOME JURISDICTIONS DO NOT ALLOW THE EXCLUSION OR LIMITATION OF INCIDENTAL OR CONSEQUENTIAL DAMAGES, SO THIS EXCLUSION AND LIMITATION MAY NOT APPLY TO YOU.8. U.S. GOVERNMENT END USERS.\r\n" + 
				"The Covered Software is a commercial item, as that term is defined in 48 C.F.R. 2.101 (Oct. 1995), consisting of commercial computer software (as that term is defined at 48 C.F.R.  252.227-7014(a)(1)) and commercial computer software documentation as such terms are used in 48 C.F.R. 12.212 (Sept. 1995). Consistent with 48 C.F.R. 12.212 and 48 C.F.R. 227.7202-1 through 227.7202-4 (June 1995), all U.S. Government End Users acquire Covered Software with only those rights set forth herein. This U.S. Government Rights clause is in lieu of, and supersedes, any other FAR, DFAR, or other clause or provision that addresses Government rights in computer software under this License.9. MISCELLANEOUS.\r\n" + 
				"This License represents the complete agreement concerning subject matter hereof. If any provision of this License is held to be unenforceable, such provision shall be reformed only to the extent necessary to make it enforceable. This License shall be governed by the law of the jurisdiction specified in a notice contained within the Original Software (except to the extent applicable law, if any, provides otherwise), excluding such jurisdictions conflict-of-law provisions. Any litigation relating to this License shall be subject to the jurisdiction of the courts located in the jurisdiction and venue specified in a notice contained within the Original Software, with the losing party responsible for costs, including, without limitation, court costs and reasonable attorneys fees and expenses. The application of the United Nations Convention on Contracts for the International Sale of Goods is expressly excluded. Any law or regulation which provides that the language of a contract shall be construed against the drafter shall not apply to this License. You agree that You alone are responsible for compliance with the United States export administration regulations (and the export control laws and regulation of any other countries) when You use, distribute or otherwise make available any Covered Software.10. RESPONSIBILITY FOR CLAIMS.\r\n" + 
				"As between Initial Developer and the Contributors, each party is responsible for claims and damages arising, directly or indirectly, out of its utilization of rights under this License and You agree to work with Initial Developer and Contributors to distribute such responsibility on an equitable basis. Nothing herein is intended or shall be deemed to constitute any admission of liability.\r\n" + 
				"\r\n" + 
				"COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL - Version 1.1)\r\n" + 
				"=====================================\r\n" + 
				"\r\n" + 
				"1. Definitions.\r\n" + 
				"1.1. \"Contributor\" means each individual or entity that creates or contributes to the creation of Modifications.\r\n" + 
				"1.2. \"Contributor Version\" means the combination of the Original Software, prior Modifications used by a Contributor (if any), and the Modifications made by that particular Contributor.\r\n" + 
				"1.3. \"Covered Software\" means (a) the Original Software, or (b) Modifications, or (c) the combination of files containing Original Software with files containing Modifications, in each case including portions thereof.\r\n" + 
				"1.4. \"Executable\" means the Covered Software in any form other than Source Code.\r\n" + 
				"1.5. \"Initial Developer\" means the individual or entity that first makes Original Software available under this License.\r\n" + 
				"1.6. \"Larger Work\" means a work which combines Covered Software or portions thereof with code not governed by the terms of this License.\r\n" + 
				"1.7. \"License\" means this document.\r\n" + 
				"1.8. \"Licensable\" means having the right to grant, to the maximum extent possible, whether at the time of the initial grant or subsequently acquired, any and all of the rights conveyed herein.\r\n" + 
				"1.9. \"Modifications\" means the Source Code and Executable form of any of the following:\r\n" + 
				"A. Any file that results from an addition to, deletion from or modification of the contents of a file containing Original Software or previous Modifications;\r\n" + 
				"B. Any new file that contains any part of the Original Software or previous Modification; or\r\n" + 
				"C. Any new file that is contributed or otherwise made available under the terms of this License.\r\n" + 
				"1.10. \"Original Software\" means the Source Code and Executable form of computer software code that is originally released under this License.\r\n" + 
				"1.11. \"Patent Claims\" means any patent claim(s), now owned or hereafter acquired, including without limitation, method, process, and apparatus claims, in any patent Licensable by grantor.\r\n" + 
				"1.12. \"Source Code means (a) the common form of computer software code in which modifications are made and (b) associated documentation included in or with such code.\r\n" + 
				"1.13. \"You\" (or \"Your\") means an individual or a legal entity exercising rights under, and complying with all of the terms of, this License. For legal entities, \"You\" includes any entity which controls, is controlled by, or is under common control with You. For purposes of this definition, \"control\" means (a) the power, direct or indirect, to cause the direction or management of such entity, whether by contract or otherwise, or (b) ownership of more than fifty percent (50%) of the outstanding shares or beneficial ownership of such entity.\r\n" + 
				"2. License Grants.\r\n" + 
				"2.1. The Initial Developer Grant.\r\n" + 
				"Conditioned upon Your compliance with Section 3.1 below and subject to third party intellectual property claims, the Initial Developer hereby grants You a world-wide, royalty-free, non-exclusive license:\r\n" + 
				"(a) under intellectual property rights (other than patent or trademark) Licensable by Initial Developer, to use, reproduce, modify, display, perform, sublicense and distribute the Original Software (or portions thereof), with or without Modifications, and/or as part of a Larger Work; and\r\n" + 
				"(b) under Patent Claims infringed by the making, using or selling of Original Software, to make, have made, use, practice, sell, and offer for sale, and/or otherwise dispose of the Original Software (or portions thereof).\r\n" + 
				"(c) The licenses granted in Sections 2.1(a) and (b) are effective on the date Initial Developer first distributes or otherwise makes the Original Software available to a third party under the terms of this License.\r\n" + 
				"(d) Notwithstanding Section 2.1(b) above, no patent license is granted: (1) for code that You delete from the Original Software, or (2) for infringements caused by: (i) the modification of the Original Software, or (ii) the combination of the Original Software with other software or devices.\r\n" + 
				"2.2. Contributor Grant.\r\n" + 
				"Conditioned upon Your compliance with Section 3.1 below and subject to third party intellectual property claims, each Contributor hereby grants You a world-wide, royalty-free, non-exclusive license:\r\n" + 
				"(a) under intellectual property rights (other than patent or trademark) Licensable by Contributor to use, reproduce, modify, display, perform, sublicense and distribute the Modifications created by such Contributor (or portions thereof), either on an unmodified basis, with other Modifications, as Covered Software and/or as part of a Larger Work; and\r\n" + 
				"(b) under Patent Claims infringed by the making, using, or selling of Modifications made by that Contributor either alone and/or in combination with its Contributor Version (or portions of such combination), to make, use, sell, offer for sale, have made, and/or otherwise dispose of: (1) Modifications made by that Contributor (or portions thereof); and (2) the combination of Modifications made by that Contributor with its Contributor Version (or portions of such combination).\r\n" + 
				"(c) The licenses granted in Sections 2.2(a) and 2.2(b) are effective on the date Contributor first distributes or otherwise makes the Modifications available to a third party.\r\n" + 
				"(d) Notwithstanding Section 2.2(b) above, no patent license is granted: (1) for any code that Contributor has deleted from the Contributor Version; (2) for infringements caused by: (i) third party modifications of Contributor Version, or (ii) the combination of Modifications made by that Contributor with other software (except as part of the Contributor Version) or other devices; or (3) under Patent Claims infringed by Covered Software in the absence of Modifications made by that Contributor.\r\n" + 
				"3. Distribution Obligations.\r\n" + 
				"3.1. Availability of Source Code.\r\n" + 
				"Any Covered Software that You distribute or otherwise make available in Executable form must also be made available in Source Code form and that Source Code form must be distributed only under the terms of this License. You must include a copy of this License with every copy of the Source Code form of the Covered Software You distribute or otherwise make available. You must inform recipients of any such Covered Software in Executable form as to how they can obtain such Covered Software in Source Code form in a reasonable manner on or through a medium customarily used for software exchange.\r\n" + 
				"3.2. Modifications.\r\n" + 
				"The Modifications that You create or to which You contribute are governed by the terms of this License. You represent that You believe Your Modifications are Your original creation(s) and/or You have sufficient rights to grant the rights conveyed by this License.\r\n" + 
				"3.3. Required Notices.\r\n" + 
				"You must include a notice in each of Your Modifications that identifies You as the Contributor of the Modification. You may not remove or alter any copyright, patent or trademark notices contained within the Covered Software, or any notices of licensing or any descriptive text giving attribution to any Contributor or the Initial Developer.\r\n" + 
				"3.4. Application of Additional Terms.\r\n" + 
				"You may not offer or impose any terms on any Covered Software in Source Code form that alters or restricts the applicable version of this License or the recipients' rights hereunder. You may choose to offer, and to charge a fee for, warranty, support, indemnity or liability obligations to one or more recipients of Covered Software. However, you may do so only on Your own behalf, and not on behalf of the Initial Developer or any Contributor. You must make it absolutely clear that any such warranty, support, indemnity or liability obligation is offered by You alone, and You hereby agree to indemnify the Initial Developer and every Contributor for any liability incurred by the Initial Developer or such Contributor as a result of warranty, support, indemnity or liability terms You offer.\r\n" + 
				"3.5. Distribution of Executable Versions.\r\n" + 
				"You may distribute the Executable form of the Covered Software under the terms of this License or under the terms of a license of Your choice, which may contain terms different from this License, provided that You are in compliance with the terms of this License and that the license for the Executable form does not attempt to limit or alter the recipient's rights in the Source Code form from the rights set forth in this License. If You distribute the Covered Software in Executable form under a different license, You must make it absolutely clear that any terms which differ from this License are offered by You alone, not by the Initial Developer or Contributor. You hereby agree to indemnify the Initial Developer and every Contributor for any liability incurred by the Initial Developer or such Contributor as a result of any such terms You offer.\r\n" + 
				"3.6. Larger Works.\r\n" + 
				"You may create a Larger Work by combining Covered Software with other code not governed by the terms of this License and distribute the Larger Work as a single product. In such a case, You must make sure the requirements of this License are fulfilled for the Covered Software.\r\n" + 
				"4. Versions of the License.\r\n" + 
				"4.1. New Versions.\r\n" + 
				"Oracle is the initial license steward and may publish revised and/or new versions of this License from time to time. Each version will be given a distinguishing version number. Except as provided in Section 4.3, no one other than the license steward has the right to modify this License.\r\n" + 
				"4.2. Effect of New Versions.\r\n" + 
				"You may always continue to use, distribute or otherwise make the Covered Software available under the terms of the version of the License under which You originally received the Covered Software. If the Initial Developer includes a notice in the Original Software prohibiting it from being distributed or otherwise made available under any subsequent version of the License, You must distribute and make the Covered Software available under the terms of the version of the License under which You originally received the Covered Software. Otherwise, You may also choose to use, distribute or otherwise make the Covered Software available under the terms of any subsequent version of the License published by the license steward.\r\n" + 
				"4.3. Modified Versions.\r\n" + 
				"When You are an Initial Developer and You want to create a new license for Your Original Software, You may create and use a modified version of this License if You: (a) rename the license and remove any references to the name of the license steward (except to note that the license differs from this License); and (b) otherwise make it clear that the license contains terms which differ from this License.\r\n" + 
				"5. DISCLAIMER OF WARRANTY.\r\n" + 
				"COVERED SOFTWARE IS PROVIDED UNDER THIS LICENSE ON AN \"AS IS\" BASIS, WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT LIMITATION, WARRANTIES THAT THE COVERED SOFTWARE IS FREE OF DEFECTS, MERCHANTABLE, FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE COVERED SOFTWARE IS WITH YOU. SHOULD ANY COVERED SOFTWARE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR CORRECTION. THIS DISCLAIMER OF WARRANTY CONSTITUTES AN ESSENTIAL PART OF THIS LICENSE. NO USE OF ANY COVERED SOFTWARE IS AUTHORIZED HEREUNDER EXCEPT UNDER THIS DISCLAIMER.\r\n" + 
				"6. TERMINATION.\r\n" + 
				"6.1. This License and the rights granted hereunder will terminate automatically if You fail to comply with terms herein and fail to cure such breach within 30 days of becoming aware of the breach. Provisions which, by their nature, must remain in effect beyond the termination of this License shall survive.\r\n" + 
				"6.2. If You assert a patent infringement claim (excluding declaratory judgment actions) against Initial Developer or a Contributor (the Initial Developer or Contributor against whom You assert such claim is referred to as \"Participant\") alleging that the Participant Software (meaning the Contributor Version where the Participant is a Contributor or the Original Software where the Participant is the Initial Developer) directly or indirectly infringes any patent, then any and all rights granted directly or indirectly to You by such Participant, the Initial Developer (if the Initial Developer is not the Participant) and all Contributors under Sections 2.1 and/or 2.2 of this License shall, upon 60 days notice from Participant terminate prospectively and automatically at the expiration of such 60 day notice period, unless if within such 60 day period You withdraw Your claim with respect to the Participant Software against such Participant either unilaterally or pursuant to a written agreement with Participant.\r\n" + 
				"6.3. If You assert a patent infringement claim against Participant alleging that the Participant Software directly or indirectly infringes any patent where such claim is resolved (such as by license or settlement) prior to the initiation of patent infringement litigation, then the reasonable value of the licenses granted by such Participant under Sections 2.1 or 2.2 shall be taken into account in determining the amount or value of any payment or license.\r\n" + 
				"6.4. In the event of termination under Sections 6.1 or 6.2 above, all end user licenses that have been validly granted by You or any distributor hereunder prior to termination (excluding licenses granted to You by any distributor) shall survive termination.\r\n" + 
				"7. LIMITATION OF LIABILITY.\r\n" + 
				"UNDER NO CIRCUMSTANCES AND UNDER NO LEGAL THEORY, WHETHER TORT (INCLUDING NEGLIGENCE), CONTRACT, OR OTHERWISE, SHALL YOU, THE INITIAL DEVELOPER, ANY OTHER CONTRIBUTOR, OR ANY DISTRIBUTOR OF COVERED SOFTWARE, OR ANY SUPPLIER OF ANY OF SUCH PARTIES, BE LIABLE TO ANY PERSON FOR ANY INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES OF ANY CHARACTER INCLUDING, WITHOUT LIMITATION, DAMAGES FOR LOSS OF GOODWILL, WORK STOPPAGE, COMPUTER FAILURE OR MALFUNCTION, OR ANY AND ALL OTHER COMMERCIAL DAMAGES OR LOSSES, EVEN IF SUCH PARTY SHALL HAVE BEEN INFORMED OF THE POSSIBILITY OF SUCH DAMAGES. THIS LIMITATION OF LIABILITY SHALL NOT APPLY TO LIABILITY FOR DEATH OR PERSONAL INJURY RESULTING FROM SUCH PARTY'S NEGLIGENCE TO THE EXTENT APPLICABLE LAW PROHIBITS SUCH LIMITATION. SOME JURISDICTIONS DO NOT ALLOW THE EXCLUSION OR LIMITATION OF INCIDENTAL OR CONSEQUENTIAL DAMAGES, SO THIS EXCLUSION AND LIMITATION MAY NOT APPLY TO YOU.\r\n" + 
				"8. U.S. GOVERNMENT END USERS.\r\n" + 
				"The Covered Software is a \"commercial item,\" as that term is defined in 48 C.F.R. 2.101 (Oct. 1995), consisting of \"commercial computer software\" (as that term is defined at 48 C.F.R. 252.227-7014(a)(1)) and \"commercial computer software documentation\" as such terms are used in 48 C.F.R. 12.212 (Sept. 1995). Consistent with 48 C.F.R. 12.212 and 48 C.F.R. 227.7202-1 through 227.7202-4 (June 1995), all U.S. Government End Users acquire Covered Software with only those rights set forth herein. This U.S. Government Rights clause is in lieu of, and supersedes, any other FAR, DFAR, or other clause or provision that addresses Government rights in computer software under this License.\r\n" + 
				"9. MISCELLANEOUS.\r\n" + 
				"This License represents the complete agreement concerning subject matter hereof. If any provision of this License is held to be unenforceable, such provision shall be reformed only to the extent necessary to make it enforceable. This License shall be governed by the law of the jurisdiction specified in a notice contained within the Original Software (except to the extent applicable law, if any, provides otherwise), excluding such jurisdiction\"s conflict-of-law provisions. Any litigation relating to this License shall be subject to the jurisdiction of the courts located in the jurisdiction and venue specified in a notice contained within the Original Software, with the losing party responsible for costs, including, without limitation, court costs and reasonable attorneys' fees and expenses. The application of the United Nations Convention on Contracts for the International Sale of Goods is expressly excluded. Any law or regulation which provides that the language of a contract shall be construed against the drafter shall not apply to this License. You agree that You alone are responsible for compliance with the United States export administration regulations (and the export control laws and regulation of any other countries) when You use, distribute or otherwise make available any Covered Software.\r\n" + 
				"10. RESPONSIBILITY FOR CLAIMS.\r\n" + 
				"As between Initial Developer and the Contributors, each party is responsible for claims and damages arising, directly or indirectly, out of its utilization of rights under this License and You agree to work with Initial Developer and Contributors to distribute such responsibility on an equitable basis. Nothing herein is intended or shall be deemed to constitute any admission of liability.\r\n" + 
				"NOTICE PURSUANT TO SECTION 9 OF THE COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)\r\n" + 
				"The code released under the CDDL shall be governed by the laws of the State of California (excluding conflict-of-law provisions). Any litigation relating to this License shall be subject to the jurisdiction of the Federal Courts of the Northern District of California and the state courts of the State of California, with venue lying in Santa Clara County, California.\r\n" + 
				"\r\n" + 
				"APACHE LICENSE, VERSION 2.0\r\n" + 
				"=====================================\r\n" + 
				"\r\n" + 
				"TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION\r\n" + 
				"1. Definitions.\r\n" + 
				"\"License\" shall mean the terms and conditions for use, reproduction, and distribution as defined by Sections 1 through 9 of this document.\r\n" + 
				"\"Licensor\" shall mean the copyright owner or entity authorized by the copyright owner that is granting the License.\r\n" + 
				"\"Legal Entity\" shall mean the union of the acting entity and all other entities that control, are controlled by, or are under common control with that entity. For the purposes of this definition, \"control\" means (i) the power, direct or indirect, to cause the direction or management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) beneficial ownership of such entity.\r\n" + 
				"\"You\" (or \"Your\") shall mean an individual or Legal Entity exercising permissions granted by this License.\r\n" + 
				"\"Source\" form shall mean the preferred form for making modifications, including but not limited to software source code, documentation source, and configuration files.\r\n" + 
				"\"Object\" form shall mean any form resulting from mechanical transformation or translation of a Source form, including but not limited to compiled object code, generated documentation, and conversions to other media types.\r\n" + 
				"\"Work\" shall mean the work of authorship, whether in Source or Object form, made available under the License, as indicated by a copyright notice that is included in or attached to the work (an example is provided in the Appendix below).\r\n" + 
				"\"Derivative Works\" shall mean any work, whether in Source or Object form, that is based on (or derived from) the Work and for which the editorial revisions, annotations, elaborations, or other modifications represent, as a whole, an original work of authorship. For the purposes of this License, Derivative Works shall not include works that remain separable from, or merely link (or bind by name) to the interfaces of, the Work and Derivative Works thereof.\r\n" + 
				"\"Contribution\" shall mean any work of authorship, including the original version of the Work and any modifications or additions to that Work or Derivative Works thereof, that is intentionally submitted to Licensor for inclusion in the Work by the copyright owner or by an individual or Legal Entity authorized to submit on behalf of the copyright owner. For the purposes of this definition, \"submitted\" means any form of electronic, verbal, or written communication sent to the Licensor or its representatives, including but not limited to communication on electronic mailing lists, source code control systems, and issue tracking systems that are managed by, or on behalf of, the Licensor for the purpose of discussing and improving the Work, but excluding communication that is conspicuously marked or otherwise designated in writing by the copyright owner as \"Not a Contribution.\"\r\n" + 
				"\"Contributor\" shall mean Licensor and any individual or Legal Entity on behalf of whom a Contribution has been received by Licensor and subsequently incorporated within the Work.\r\n" + 
				"2. Grant of Copyright License. Subject to the terms and conditions of this License, each Contributor hereby grants to You a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable copyright license to reproduce, prepare Derivative Works of, publicly display, publicly perform, sublicense, and distribute the Work and such Derivative Works in Source or Object form.\r\n" + 
				"3. Grant of Patent License. Subject to the terms and conditions of this License, each Contributor hereby grants to You a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable (except as stated in this section) patent license to make, have made, use, offer to sell, sell, import, and otherwise transfer the Work, where such license applies only to those patent claims licensable by such Contributor that are necessarily infringed by their Contribution(s) alone or by combination of their Contribution(s) with the Work to which such Contribution(s) was submitted. If You institute patent litigation against any entity (including a cross-claim or counterclaim in a lawsuit) alleging that the Work or a Contribution incorporated within the Work constitutes direct or contributory patent infringement, then any patent licenses granted to You under this License for that Work shall terminate as of the date such litigation is filed.\r\n" + 
				"4. Redistribution. You may reproduce and distribute copies of the Work or Derivative Works thereof in any medium, with or without modifications, and in Source or Object form, provided that You meet the following conditions:\r\n" + 
				"You must give any other recipients of the Work or Derivative Works a copy of this License; and\r\n" + 
				"You must cause any modified files to carry prominent notices stating that You changed the files; and\r\n" + 
				"You must retain, in the Source form of any Derivative Works that You distribute, all copyright, patent, trademark, and attribution notices from the Source form of the Work, excluding those notices that do not pertain to any part of the Derivative Works; and\r\n" + 
				"If the Work includes a \"NOTICE\" text file as part of its distribution, then any Derivative Works that You distribute must include a readable copy of the attribution notices contained within such NOTICE file, excluding those notices that do not pertain to any part of the Derivative Works, in at least one of the following places: within a NOTICE text file distributed as part of the Derivative Works; within the Source form or documentation, if provided along with the Derivative Works; or, within a display generated by the Derivative Works, if and wherever such third-party notices normally appear. The contents of the NOTICE file are for informational purposes only and do not modify the License. You may add Your own attribution notices within Derivative Works that You distribute, alongside or as an addendum to the NOTICE text from the Work, provided that such additional attribution notices cannot be construed as modifying the License. \r\n" + 
				"You may add Your own copyright statement to Your modifications and may provide additional or different license terms and conditions for use, reproduction, or distribution of Your modifications, or for any such Derivative Works as a whole, provided Your use, reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.\r\n" + 
				"5. Submission of Contributions. Unless You explicitly state otherwise, any Contribution intentionally submitted for inclusion in the Work by You to the Licensor shall be under the terms and conditions of this License, without any additional terms or conditions. Notwithstanding the above, nothing herein shall supersede or modify the terms of any separate license agreement you may have executed with Licensor regarding such Contributions.\r\n" + 
				"6. Trademarks. This License does not grant permission to use the trade names, trademarks, service marks, or product names of the Licensor, except as required for reasonable and customary use in describing the origin of the Work and reproducing the content of the NOTICE file.\r\n" + 
				"7. Disclaimer of Warranty. Unless required by applicable law or agreed to in writing, Licensor provides the Work (and each Contributor provides its Contributions) on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied, including, without limitation, any warranties or conditions of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE. You are solely responsible for determining the appropriateness of using or redistributing the Work and assume any risks associated with Your exercise of permissions under this License.\r\n" + 
				"8. Limitation of Liability. In no event and under no legal theory, whether in tort (including negligence), contract, or otherwise, unless required by applicable law (such as deliberate and grossly negligent acts) or agreed to in writing, shall any Contributor be liable to You for damages, including any direct, indirect, special, incidental, or consequential damages of any character arising as a result of this License or out of the use or inability to use the Work (including but not limited to damages for loss of goodwill, work stoppage, computer failure or malfunction, or any and all other commercial damages or losses), even if such Contributor has been advised of the possibility of such damages.\r\n" + 
				"9. Accepting Warranty or Additional Liability. While redistributing the Work or Derivative Works thereof, You may choose to offer, and charge a fee for, acceptance of support, warranty, indemnity, or other liability obligations and/or rights consistent with this License. However, in accepting such obligations, You may act only on Your own behalf and on Your sole responsibility, not on behalf of any other Contributor, and only if You agree to indemnify, defend, and hold each Contributor harmless for any liability incurred by, or claims asserted against, such Contributor by reason of your accepting any such warranty or additional liability.");
	}
}
