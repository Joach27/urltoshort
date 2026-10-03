import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-short-url-form-component',
  styleUrl: './short-url-form-component.css',
  templateUrl: './short-url-form-component.html',
})
export class ShortUrlFormComponent {
  shortUrlForm = new FormGroup({
    input: new FormControl(''),
    result: new FormControl({value: '', disabled: true})
  })

  onSubmit() {
    const input = this.shortUrlForm.value.input;
    if (!input) {
      return;
    }
    
    const result = this.getResult(input);
    this.shortUrlForm.controls.result.setValue(result);
  }

  copyResult(): void{
    const result = this.shortUrlForm.controls.result.value;

    if (result) {
      navigator.clipboard.writeText(result);
    }
  }

  getResult(input: string): string{
    return "Logic";
  }
}
