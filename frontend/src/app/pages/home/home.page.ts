import { Component } from '@angular/core';
import { ShortUrlFormComponent } from '../../components/short-url-form-component/short-url-form-component';

@Component({
  imports: [ShortUrlFormComponent],
  selector: 'app-home',
  styleUrl: './home.page.css',
  templateUrl: './home.page.html',
})
export class HomePage {}
