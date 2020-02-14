def call(Closure closure) {

   // I define a series of emojis icon names to provide to the thread.
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
   // Pick a random one.
   def extracted = threads[randomIndex]
   // Split the lists tokens into separate variables.
   def lista = env.JOB_NAME.tokenize('/')

   def project = lista[lista.size() - 2]
   def testTarget = lista[lista.size() - 1]
   def platform = lista[lista.size() - 3]

   // Provide a nicer icon to the platform tested.
   if (platform.toLowerCase().contains("iOS".toLowerCase())) {
      platform += " :green_apple:"
   } else {
      platform += " :robot_face:"
   }

   def computerIcon = ""
   // Same as before for the machin used for the testing.
   if (NODE_NAME.toLowerCase().contains("mac mini")) {
      computerIcon = ":desktop_computer:"
   } else {
      computerIcon = ":computer:"
   }

   // handle the exception thrown by the closure
   try {

     slackSend color: '#0078ff',
        message: """
Thread: ${extracted}
Platform: ${platform}
Project: *${project}* --> _${testTarget}_
Build: #${env.BUILD_NUMBER}
Status: :rocket: *Started*
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

      return 0
   }
   catch (Exception | AssertionError exc) {
     // I'm checking if the current build was interrupted or not
     if (exc instanceof InterruptedException) {

        slackSend color: '#ffa700',
           message: """
   Thread: ${extracted}
   Platform: ${platform}
   Project: *${project}* --> _${testTarget}_
   Build: #${env.BUILD_NUMBER}
   Status: :stopwatch: *Interrupted*
   Node: ${computerIcon} ${NODE_NAME}
   See: <${BUILD_URL}|here>
        """;

         return 0
    } else {
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

       return 400
    }
   }
}
