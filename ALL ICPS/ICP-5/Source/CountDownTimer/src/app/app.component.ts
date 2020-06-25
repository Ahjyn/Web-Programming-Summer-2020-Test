/****************************************************************************/
/* Profile: SangJin Hyun                                                     */
/* Assignment: ICP - 5 : Part2 - CountDownTimer                              */
/* File: Create a basic timer using Angular that countdowns a specific date   */
/****************************************************************************/

import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'CountDownTimer';

  countDownDate = new Date('July 4, 2020 12:00:00').getTime(); // static declaration of date (enter some date)
  demo: any;
  x = setInterval(() => {
    const present = new Date().getTime(); // call to date and construction
    const distance = this.countDownDate - present;
    const days = Math.floor(distance / (1000 * 60 * 60 * 24));  //  constant values for each division of time
    const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((distance % (1000 * 60 * 60) / (1000 * 60 * 60)));
    const seconds = Math.floor((distance % (1000 * 60)) / 1000);
    this.demo = days + 'd ' + hours + 'h ' + minutes + 'm ' + seconds + 's ' ;
  });
}
