import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent implements OnInit {
  sideNav : boolean = false

  constructor() { }

  ngOnInit(): void {
  }

  toggleNav(){
    this.sideNav = !this.sideNav

  }

}
