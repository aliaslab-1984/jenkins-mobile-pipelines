def call(Closure closure) {
   
   def threads = [":large_green_circle:", 
              ":large_yellow_circle:",
              ":large_purple_circle:",
              ":large_brown_circle:",
              ":large_orange_circle:",
              ":large_green_square:", 
              ":large_yellow_square:",
              ":large_purple_square:",
              ":large_brown_square:",
              ":large_orange_square:",
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
