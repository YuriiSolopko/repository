# My training projects
TAXI_SERVICE description:<br>
Web interface for taxi service.<br>
Written with the usage of : Java, Spring, Hibernate, HTML, CSS, JSTL, JSP<br>
Project structure:<br>
<b>webapp</b> - web part written with the use JSP pages (WEB-INF/jsps), HTML, CSS, JSTL<br>
<b>java</b><br>
|-com.js.taxi<br>
<span>  6 </span>|- domain - domain objects for database (Oracle 10), configured with javax.persistence annotations<br>
  |- dao - data access objects (interfaces and their implementations), configured with Spring's bean annotations(@Repository), written with the usage of Hibernate framework<br>
  |- exeption - exeptions for services<br>
  |- service - services (interfaces and their implementations), configured with Spring's bean annotations(@Service)<br>
  |- controller - Spring controllers (@Controller)<br>
<b>resourses</b><br>
|-conf.properties - properties for Spring's context<br>
|-log4j.properties - properties for Log4j<br>
