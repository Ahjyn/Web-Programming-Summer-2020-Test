/****************************************************************************/
/* Profile: SangJin Hyun                                                     */
/* Assignment: ICP - 5 : Part2 - ToDoApplication                             */
/* File: Create a basic ToDoList application that can add or delete tasks    */
/****************************************************************************/

import { Component } from '@angular/core';

@Component({ //decorator
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ToDoApplication';
  todo = [
    {
      label: 'Test Value (click delete to erase)',

    },
  ];

  addTodo(newTodoLabel){

    var newTodo = {
      label: newTodoLabel,
    };
    this.todo.push(newTodo);
  }

  deleteTodo(todo){
    this.todo = this.todo.filter(t => t.label !== todo.label);
  }
}
