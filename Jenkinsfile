library 'jenkins_shared'

pipeline {
    agent { label 'integration' }
    parameters {
        string(defaultValue: "PCK.AUTOMIC_ACRONIS", description: 'Package Name', name: 'package_name')
        string(defaultValue: "Package.Acronis", description: 'lifecycleentity_name', name: 'lifecycleentity_name')
        string(defaultValue: "package.acronis", description: 'le_name_lowercase', name: 'le_name_lowercase')
        string(defaultValue: "pck_acronis", description: 'le_technical_name', name: 'le_technical_name')
        string(defaultValue: "pck.automic_acronis", description: 'component_name', name: 'component_name')
      	string(defaultValue: "packages/$params.package_name", description: 'repository name', name: 'repo_name')
        string(defaultValue: "pck.automic_acronis.test", description: 'Pack Test Name', name: 'pck_test_name')
        string(defaultValue: "ee1f0b7a-ab60-42f8-808c-43e676cea993", description: 'Maven settings file key', name: 'mvn_settings_file_key')
		string(name: 'project_name', defaultValue: "", description: 'Project name at the BlackDuck Server')
        booleanParam(name: 'delete_logs', defaultValue: true, description: 'Whether to delete the logs from Detect utility run or not')
	booleanParam(name: 'black_duck_scan', defaultValue: false, description: 'Whether to run the black duck scan or not')
    }
    stages {
        stage('Build ActionPack') {
            steps {
				script {
					buildParameters()
					env.branch_type=utilities.detectBranch(env.GIT_BRANCH)
					
					// detecting apdk build version_number
					String version_number = powershell label: 'Detect Plugin Version', returnStdout: true, script: '''
					#Check Pom XML
					[xml]$XmlDocument = Get-Content -Path pom.xml
					$version_number=$XmlDocument.\'project\'.\'version\'
					Write-Host $version_number
					return $version_number'''
					env.version_number=version_number.trim()
					
					//check mandatory fields
					powershell label: 'Check Mandatory Fields', script: '''
					echo "LCE name:  $env:lifecycleentity_name"
					# LCE name has to be set
					if ([string]::IsNullOrEmpty("${env:lifecycleentity_name}")) {
					echo "Error: lifecycleentity_name has to be set."
					Exit 1
					} '''
					
					//building tool
					withMaven(mavenSettingsConfig: env.mvn_settings_file_key, options: [artifactsPublisher(disabled: true)]) {
							bat label: 'Building Tool', script: 'pushd "%WORKSPACE%/" && mvn clean package -Dplugin.version=%version_number% -Dbuild.number=%BUILD_NUMBER% -Dbuild.revision=%GIT_COMMIT% -DskipTests -gs settings.xml'
					}
					
					
					//Copy zip in build dir
					bat label: 'Create Zip', script: '''
					if exist %build_dir% (
						echo "%build_dir% directory already exist. Removing it."
						rmdir /S/Q %build_dir%
					)
					mkdir "%build_dir%"
					echo "Building %package_name% zip file"
					copy target\\%package_name%-%version_number%.zip %build_dir%'''
					
					//rename zip
					String output_filename = powershell label: 'Rename Zip', returnStdout: true, script: '''
					$version = "${env:version_number}".replace(".","_")
					Write-Host "Lifecycle Entity name: ${env:lifecycleentity_name}"
					$output_filename = "${env:lifecycleentity_name}_${env:package_name}_$version+build.${env:BUILD_NUMBER}.zip"
					rename-item -Path ${env:build_dir}\\${env:package_name}-${env:version_number}.zip -newName $output_filename
					Write-Host "output filename $output_filename"
					return $output_filename'''
					env.output_filename = output_filename.trim()
					
					//Archiving Artifact to Jenkins
					utilities.archiveArtifact(env.archive_artifact)
					
					//Runnig BlackDuck Scan
					if(env.black_duck_scan){
						utilities.blackDuckScan(env.project_name, env.delete_logs)
					}
					
					
				}
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
