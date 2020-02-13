def call(Closure closure) {
   
   def threads = [":one:", 
              ":two:",
              ":three:",
              ":four:",
              ":five:",
              ":six:", 
              ":seven:",
              ":eight:",
              ":nine:",
              ":keycap_ten:",
              ]

   Random random = new Random()
   def randomIndex = random.nextInt(threads.size() - 1)
   def extracted = threads[randomIndex]
   
   try {
     slackSend color: '#ffff00', message: """
     ${extracted} ${env.JOB_NAME}
     Build: #${env.BUILD_NUMBER}
     Status: *Started* on :computer: ${NODE_NAME}
     See: <${BUILD_URL}|here>
     """;

     closure();

     slackSend color: 'good', message: """
     ${extracted} ${env.JOB_NAME}
     Build: #${env.BUILD_NUMBER}
     Status: :tada: *Succeded* :tada: on ${NODE_NAME}
     See: <${BUILD_URL}|here>
     """;
   }
   catch (Exception | AssertionError exc) {
     slackSend color: '#ff0000', message: """
     ${extracted} ${env.JOB_NAME}
     Build: #${env.BUILD_NUMBER}
     Status: :boom: *Failed* on ${NODE_NAME}
     See: <${BUILD_URL}|here>
     Additional Info: `${exc.message}`
     """;
   }
}
