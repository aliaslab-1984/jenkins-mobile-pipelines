def call(Closure closure) {
   try {
     slackSend color: '#ffff00', message: """
     ${env.JOB_NAME}
     Build: #${env.BUILD_NUMBER}
     Status: *Started* on :computer: ${NODE_NAME}
     See: <${BUILD_URL}|here>
     """;

     closure();

     slackSend color: 'good', message: """
     ${env.JOB_NAME}
     Build: #${env.BUILD_NUMBER}
     Status: :tada: *Succeded* :tada: on ${NODE_NAME}
     See: <${BUILD_URL}|here>
     """;
   }
   catch (Exception | AssertionError exc) {
     slackSend color: '#ff0000', message: """
     ${env.JOB_NAME}
     Build: #${env.BUILD_NUMBER}
     Status: :boom: *Failed* on ${NODE_NAME}
     See: <${BUILD_URL}|here>
     Additional Info: `${exc.message}`
     """;
   }
}
