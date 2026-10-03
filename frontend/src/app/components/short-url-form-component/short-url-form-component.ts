import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LinkService } from '../../services/link';

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

  // Copy state
  isCopied = signal(false);

  // Inject link service
  private linkService = inject(LinkService);

  onSubmit() {
    const input = this.shortUrlForm.value.input;
    if (!input) {
      return;
    }
    
    this.linkService.shortenUrl(input!).subscribe({
      next: (response) => {
        this.shortUrlForm.controls.result.setValue(response.shortUrl)
      },

      error: (err) => {
        console.error(err);
      }
    })

    
  }

  copyResult(): void{
    const result = this.shortUrlForm.controls.result.value;

    if (result) {
      navigator.clipboard.writeText(result).then(() => {
        this.isCopied.set(true);
        setTimeout(() => this.isCopied.set(false), 2000);
      });
    }
  }
}
