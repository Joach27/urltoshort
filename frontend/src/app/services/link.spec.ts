import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { LinkService } from './link';

describe('LinkService', () => {
  let service: LinkService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(LinkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
