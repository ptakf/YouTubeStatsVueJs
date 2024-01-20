import { Component } from '@angular/core';
import { type NgForm } from '@angular/forms';
import { HttpclientService } from '../../services/httpclient.service';

@Component({
  selector: 'app-test-form',
  standalone: false,
  templateUrl: './test-form.component.html',
  styleUrl: './test-form.component.scss',
})
export class TestFormComponent {
  constructor(private httpclientService: HttpclientService) {}

  data: any = {};

  onClick(inputForm: NgForm): void {
    if (inputForm.valid === true) {
      this.httpclientService
        .askInfo(this.data.dataInput as number)
        .subscribe((response: any) => {
          this.data.dataOutput = response.result;
        });
    }
  }
}
