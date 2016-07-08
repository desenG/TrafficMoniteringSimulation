## Description
This is a Java/RMI software product to simulate Traffic Monitoring System with one Main Monitor Centre and 3 region monitor centres.

Main Monitor Centre is as following:
![Main Monitor Centre](https://github.com/desenG/TrafficMoniteringSimulation/blob/master/imgs/main%20moinitor%20center.png?raw=true "Main Monitor Centre")

In above image, The color moving balls represent moving cars among three regions. In this window, any region will be monitored by adding it to window as panel. Now, you can see, three regions are all added to the window.

Technically, I use ServerController to start with 10 random color and direction and speed cars in each region. All the cars keep moving and their color and new position are all recorded in MySQL. You will also notice that all the cars will able to travel among the three regions.

Start Region monitor as following:
</br>
![Start Region monitor](https://github.com/desenG/TrafficMoniteringSimulation/blob/master/imgs/start%20region%20monitor%20center.PNG?raw=true "Start Region monitor")

Then You will see as following:
![Region monitor](https://github.com/desenG/TrafficMoniteringSimulation/blob/master/imgs/region%20monitor%20center.PNG?raw=true " Region monitor")

