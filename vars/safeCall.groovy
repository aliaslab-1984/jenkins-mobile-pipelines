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
   
   def computerIcon = ""
   
   if (NODE_NAME.toLowerCase().contains("mac mini")) {
      computerIcon = ":desktop_computer:"
   } else {
      computerIcon = ":computer:"
   }
   
   try {
      
     slackSend color: '#ffff00',
        message: """
Thread: ${extracted}
Platform: ${platform}
Project: *${project}* --> _${testTarget}_
Build: #${env.BUILD_NUMBER}
Status: *Started* 
Node: ${computerIcon} ${NODE_NAME}
More info: <${BUILD_URL}|here>
     """;

     closure();

     slackSend color: 'good',
        message: """
Thread: ${extracted}
Platform: ${platform}
Project: *${project}* --> _${testTarget}_
Build: #${env.BUILD_NUMBER}
Status: :tada: *Succeded* :tada: 
Node: ${computerIcon} ${NODE_NAME}
More info: <${BUILD_URL}|here>
     """;
   }
   catch (Exception | AssertionError exc) {
     slackSend color: '#ff0000',
        message: """
Thread: ${extracted}
Platform: ${platform}
Project: *${project}* --> _${testTarget}_
Build: #${env.BUILD_NUMBER}
Status: :boom: *Failed*
Node: ${computerIcon} ${NODE_NAME}
See: <${BUILD_URL}|here>
Additional Info: `${exc.message}`
     """;
   }
}
