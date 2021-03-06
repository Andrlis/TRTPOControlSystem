# Часть 1
## 1.	Тип приложения
Веб-приложение – клиент-серверное приложение, в котором клиент взаимодействует с сервером при помощи браузера, а за сервер отвечает – веб-сервер.
## 2.	Стратегия развёртывания 
Нераспределенное развертывание.
## 3. Выбор технологии
  - Java – самый распространенный язык для веб-сервисов. Преимущества – простота применения, независимость от платформы и встроенные функции.
  - JS – предоставляет возможности для создания клиентской части приложения, поддерживает AJAX.
  - jQuery – помогает легко получать доступ к любому элементу DOM, обращаться к атрибутам и содержимому элементов DOM, манипулировать ими. 
  - JSP – серверная технология для создания веб-страниц. Используется при создании динамических странц и предоставляет возможность горячего развертывания.
  - Hibernate – освобождение разработчика от значительного объёма сравнительно низкоуровневого программирования при работе в объектно-ориентированных средствах в реляционной базе данных.
  - Apache Tomcat - контейнер сервлетов с открытым исходным кодом. Реализует спецификацию сервлетов, спецификацию JSP и JavaServer Faces(JSF). Tomcat позволяет запускать веб-приложения, содержит ряд программ для самоконфигурирования.
## 4. Показатели качества
  - Безопасность;
  - Корректное отображение интерфейса;
  - Скорость работы;
  - Надежность;
  - Актуальность;
## 5.  Пути реализации сквозной функциональности: 
  - Аутентификация: обеспечить безопасность при входе путем использования только post-запросов и хэширования паролей.
  - Авторизация: защита ресурсов посредством авторизации вызывающей стороны.
  - Сетевое взаимодействие: выбирать соответствующие транспортные протоколы
  - Хэширование: применить алгоритм SHA-1, который позволяет шифровать пароли без возможности разшифрования.
 ## 6. To be architecture:
 1. Диаграмма компонентов.
![alt text](../../resource/Component-Deployment.png)
 2. Диаграмма классов.
![alt text](../../resource/CD1.jpg)
 3. Структура базы данных.
![alt text](../../resource/DB1.jpg)

 # Часть 2
 ## As is architecture:
  1. Диаграмма компонентов.
![alt text](../../resource/Component-Deployment.png)
 2. Диаграмма классов.
![alt text](../../resource/CD2.jpg)
 3. Структура базы данных.
![alt text](../../resource/DB2.jpg)
 # Часть 3
 ## Вывод:
   Добавление в приложение нового функционала вызвало внесение некоторых корректировок в структуру БД и привело к изменениям архитектуры, которые заметны на диаграммах классов.
   Структура классов упрощена. Были убраны лишние зависимости и в структуре БД.
   При проектировании не в полнной мере получается оценить препятсвия и ошибки, которые могут возникнуть во время реализации и проявиться лишь на этапе тестирования. Поэтому некоторые решения нельзя назвать спланированными. Они используются для устраниния ошибок, и не всегда однозначно верные, так как могут ухудшать гибкость и расширяемость приложения.    
   
