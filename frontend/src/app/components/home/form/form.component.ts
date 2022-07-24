import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.sass']
})
export class FormComponent implements OnInit {
  username = new FormControl('', [Validators.required]);
  hackathon : String = '';
  applyFormGroup : FormGroup = this.fb.group({
    username:["", Validators.required],
    hackathon:[0, Validators.required],
  })

  hackathons = [
    {
      title : "Energy Hackathon",
      body  : "Energy based hackathon"
    },

    {
      title : "Water saving Hackathon",
      body  : "Water saving based hackathon"
    },
  ]

  constructor(private fb: FormBuilder,private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get("http://localhost:8080/api/v1/hackathon").subscribe({
      next: (response )=>{console.log(response)},
      error: (response)=>{console.log(response)}
    })
  }

  getErrorMessage() {
    return this.username.hasError('required') ? 'Field cannot be empty' : '';
  }

}
