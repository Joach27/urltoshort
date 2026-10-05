import { Component, computed, effect, inject, input, output, signal } from '@angular/core';
import { NgIcon, provideIcons } from '@ng-icons/core';
import { heroXMark } from '@ng-icons/heroicons/outline';
import { LinkService } from '../../services/link';

// Matches the backend LinkAnalyticsResponse: the two maps are serialized by
// Jackson as JSON objects (e.g. { "MOBILE": 3 }, { "France": 80 }).
interface AnalyticsResponse {
  totalClicks: number;
  clicksByDevice: Record<string, number>;
  clicksByCountry: Record<string, number>;
}

@Component({
  imports: [NgIcon],
  viewProviders: [provideIcons({ heroXMark })],
  selector: 'app-analytics-modal-component',
  styleUrl: './analytics-modal-component.css',
  templateUrl: './analytics-modal-component.html',
})
export class AnalyticsModalComponent {
  private linkService = inject(LinkService);

  // Inputs
  isOpen = input(false);
  title = input('');
  linkId = input<number | null>(null);

  // Output
  close = output<void>();

  // Internal state
  isLoading = signal(false);
  error = signal<string | null>(null);
  analytics = signal<AnalyticsResponse | null>(null);

  // Flatten the backend maps so they can be iterated with @for
  deviceEntries = computed(() =>
    Object.entries(this.analytics()?.clicksByDevice ?? {}).map(([device, clicks]) => ({
      device,
      clicks,
    }))
  );
  countryEntries = computed(() =>
    Object.entries(this.analytics()?.clicksByCountry ?? {}).map(([country, clicks]) => ({
      country,
      clicks,
    }))
  );

  constructor() {
    effect(() => {
      const open = this.isOpen();
      const id = this.linkId();

      // Modal closed : reset everything
      if (!open) {
        this.analytics.set(null);
        this.error.set(null);
        this.isLoading.set(false);
        return;
      }

      // Modal opened with a link : fetch its analytics
      if (id != null) {
        this.fetchAnalytics(id);
      }
    });
  }

  private fetchAnalytics(linkId: number) {
    this.isLoading.set(true);
    this.error.set(null);
    this.analytics.set(null);

    this.linkService.getLinkAnalytics(linkId).subscribe({
      next: (data) => {
        this.analytics.set(data);
        this.isLoading.set(false);
      },
      error: (err) => {
        console.error('Cannot fetch analytics', err);
        this.error.set('Unable to load analytics. Please try again.');
        this.isLoading.set(false);
      },
    });
  }

  onClose() {
    this.close.emit();
  }
}
