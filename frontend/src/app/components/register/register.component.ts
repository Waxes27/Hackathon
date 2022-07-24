import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass']
})
export class RegisterComponent implements OnInit {

  private submitted: boolean = false; // keep track on whether form is submitted
  private events: any[] = []; // use later to display form changes


  registrationForm = new FormGroup({
    username: new FormControl('',[<any>Validators.required]),
    password: new FormControl('', [<any>Validators.required, <any>Validators.minLength(6)]),
  });

  constructor(private http : HttpClient) { }

  ngOnInit(): void {
  }

  onSubmit(){
    var formData: any = new FormData();
    formData.append('username', this.registrationForm.get('username')?.value);
    formData.append('password', this.registrationForm.get('password')?.value);
    formData.append('email', this.registrationForm.get('username')?.value + "@student.wethinkcode.co.za");

    this.http
      .post('http://localhost:8080/registration', formData)
      .subscribe({
        next: (response) => console.log(response),
        error: (error) => console.log(error),
      });
    
  }

}
