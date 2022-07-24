import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {
  // username : String = ""
  // password : String = ""
  
  private submitted: boolean = false; // keep track on whether form is submitted
  private events: any[] = []; // use later to display form changes


  loginForm = new FormGroup({
    username: new FormControl('',[<any>Validators.required]),
    password: new FormControl('', [<any>Validators.required, <any>Validators.minLength(6)]),
  });

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    
  }

  onSubmit(){
    var formData: any = new FormData();
    formData.append('username', this.loginForm.get('username')?.value);
    formData.append('password', this.loginForm.get('password')?.value);

    this.http
      .post('http://waxes27.com:8080/login', formData)
      .subscribe({
        next: (response) => console.log(response),
        error: (error) => console.log(error),
      });
    
  }

}
