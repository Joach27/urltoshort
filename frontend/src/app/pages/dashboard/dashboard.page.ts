import { Component, inject, OnInit, signal } from '@angular/core';
import { LinkService } from '../../services/link';
import { RouterLink } from '@angular/router';
import { AnalyticsModalComponent } from '../../components/analytics-modal-component/analytics-modal-component';

@Component({
  imports: [RouterLink, AnalyticsModalComponent],
  selector: 'app-dashboard',
  styleUrl: './dashboard.page.css',
  templateUrl: './dashboard.page.html',
})
export class DashboardPage {
  private linkService = inject(LinkService);

  // Signals for interactive displaying
  links = signal<any[]>([]);
  isLoading = signal<boolean>(true);

  /* ------------------ This part concerns the data analytics --------- */
  // Signal to check modal state
  isModalOpen = signal(false);
  selectedShortLink = signal<string>(''); // For displaying the title
  selectedLinkId = signal<number | null>(null); // Which link to load analytics for

  /* ----------------- Analytics inits ends here ----------------- */

  // ngOnInit runs automaticaly when dashboard page opens
  ngOnInit() {
    this.fetchLinks();
  }

  // Fetch links for user {linkId, targetUrl, shortUrl}
  fetchLinks() {
    this.linkService.getUserLinks().subscribe({
      next: (data) => {
        this.links.set(data); // Add links to links array
        this.isLoading.set(false);
      },

      error: (err) => {
        console.error("Loading failed", err);
        this.isLoading.set(false);
      }
    })
  }

  // Called when Get Analytics get clicked
  openAnalytics(linkId: number, shortLink: string) {
    this.selectedShortLink.set(shortLink);
    this.selectedLinkId.set(linkId);
    this.isModalOpen.set(true);
    // The modal itself fetches and displays the analytics for this linkId
  }

  // Closing the Modal
  closeModal() {
    this.isModalOpen.set(false);
    this.selectedLinkId.set(null);
    this.selectedShortLink.set('');
  }
}
