mvn clean package; scp target/backend-2.1.1.RELEASE.jar ubuntu@ec2-18-194-181-83.eu-central-1.compute.amazonaws.com:/home/ubuntu/jar/ ;ssh ubuntu@ec2-18-194-181-83.eu-central-1.compute.amazonaws.com "killall java; java -jar /home/ubuntu/jar/backend-2.1.1.RELEASE.jar"


# https://slack.com/oauth/authorize?client_id=571141609170.571635956869&scope=channels:history,groups:history,bot,users:read,im:history,im:read,channels:read,groups:read,users.profile:read&state=I0axGKvuz0EKLBb6zoYq



# https://slack.com/oauth/authorize?client_id=571141609170.571635956869&scope=channels:history,bot,users:read,channels:read,users.profile:read&state=I0axGKvuz0EKLBb6zoYq
