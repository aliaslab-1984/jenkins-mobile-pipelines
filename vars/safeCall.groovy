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
               ":dog:",
               ":dog2:",
               ":hotdog:",
               ":doughnut",
               ":cat:",
               ":cat2:",
               ":candy:",
               ":cactus:",
               ":camel:",
               ":poop:",
               ":poodle:"
              ]

   Random random = new Random()
   def randomIndex = random.nextInt(threads.size() - 1)
   def extracted = threads[randomIndex]
   def lista = env.JOB_NAME.tokenize('/')
   
   def project = lista[lista.size() - 2]
   def testTarget = lista[lista.size() - 1]
   def platform = lista[lista.size() - 3]
   
   if (platform.toLowerCase().contains("iOS".toLowerCase())) {
      platform += " :green_apple:"
   } else {
      platform += " :robot_face:"
   }
   
   try {
      
     slackSend color: '#ffff00', message: """
     Thread: ${extracted}
     Platform: ${platform}
     Project: *${project}* --> ${testTarget}
     Build: #${env.BUILD_NUMBER}
     Status: *Started* on :computer: ${NODE_NAME}
     See: <${BUILD_URL}|here>
     """;

     closure();

     slackSend color: 'good', message: """
     Thread: ${extracted}
     Platform: ${platform}
     Project: *${project}* --> ${testTarget}
     Build: #${env.BUILD_NUMBER}
     Status: :tada: *Succeded* :tada: on ${NODE_NAME}
     See: <${BUILD_URL}|here>
     """;
   }
   catch (Exception | AssertionError exc) {
     slackSend color: '#ff0000', message: """
     Thread: ${extracted}
     Platform: ${platform}
     Project: *${project}* --> ${testTarget}
     Build: #${env.BUILD_NUMBER}
     Status: :boom: *Failed* on ${NODE_NAME}
     See: <${BUILD_URL}|here>
     Additional Info: `${exc.message}`
     """;
   }
}
