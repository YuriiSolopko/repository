# My training projects
TAXI_SERVICE description:
Web interface for taxi service.
Project structure:
<b>webapp</b> - web part written with the use JSP pages (WEB-INF/jsps), HTML, CSS, JSTL
java
|-com.js.taxi
  |- domain - domain objects for database (Oracle 10), configured with javax.persistence annotations
  |- dao - data access objects (interfaces and their implementations), configured with Spring's bean annotations(@Repository)
  |- exeption - exeptions for services
  |- service - services (interfaces and their implementations), configured with Spring's bean annotations(@Service)
  |- controller - Spring controllers (@Controller)
resourses
