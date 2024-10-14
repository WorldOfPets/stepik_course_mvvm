### Задачи
1. Реализовать функцию чтобы локальные изменения сделанные пользователем без интернета синхрнизировались с сервером
```kotlin
class TaskServerApi :TaskInterface {
---
  override fun syncData(listTasks: List<TaskModel>, callback: MyCustomCallback<TaskModel>) {
      TODO("Not yet implemented")
      }
}
```
2. Создать таблицу для хранения ошибок как на сервере так и локально (имя, причина, дата и время, класс в котором она была вызвана, id, тип)
3. Весь код обернуть в конструкцию
```kotlin
try{
//your code
}catch(ex:Excecption){
//your exception handler
}
```
4. Все ошибки должны фиксироваться и отправляться на сервер при подключении к интернету
