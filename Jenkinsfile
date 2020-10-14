library 'jenkins_shared'

pipeline {
    agent { label 'integration' }
    parameters {
        string(defaultValue: "PCK.AUTOMIC_ACRONIS", description: 'Package Name', name: 'package_name')
        string(defaultValue: "Package.ACRONIS", description: 'lifecycleentity_name', name: 'lifecycleentity_name')
        string(defaultValue: "package.acronis", description: 'le_name_lowercase', name: 'le_name_lowercase')
        string(defaultValue: "pck_acronis", description: 'le_technical_name', name: 'le_technical_name')
        string(defaultValue: "pck.automic_acronis", description: 'component_name', name: 'component_name')
      	string(defaultValue: "packages/$params.package_name", description: 'repository name', name: 'repo_name')
        string(defaultValue: "pck.automic_acronis.test", description: 'Pack Test Name', name: 'pck_test_name')
        string(defaultValue: "ee1f0b7a-ab60-42f8-808c-43e676cea993", description: 'Maven settings file key', name: 'mvn_settings_file_key')
    }
    stages {
        stage('Build ActionPack') {
            steps {
                buildParameters()
                script {
                    env.tool_dir="."
					env.apdk_build=true
                }
                apBuildTemplate()
            }              
        }
		
		stage('Release ActionPack') {
			when { 
			    expression { return env.branch_type == 'release' }
			}
            steps {
                apReleaseTemplate()
            }       
                   
        }
    }
}
