import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgIcon, provideIcons } from '@ng-icons/core';
import { heroArrowTopRightOnSquare } from '@ng-icons/heroicons/outline';
import { LinkService } from '../../services/link';

@Component({
  imports: [ReactiveFormsModule, NgIcon],
  viewProviders: [provideIcons({ heroArrowTopRightOnSquare })],
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

  // Short link result, exposed as a signal so the template reacts
  // (the app runs zoneless, a plain FormControl value does not trigger CD)
  shortUrl = signal<string>('');

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
        this.shortUrl.set(response.shortUrl)
      },

      error: (err) => {
        console.error(err);
      }
    })

    
  }

  copyResult(): void{
    const result = this.shortUrl();

    if (result) {
      navigator.clipboard.writeText(result).then(() => {
        this.isCopied.set(true);
        setTimeout(() => this.isCopied.set(false), 2000);
      });
    }
  }
}
