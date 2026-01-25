import { Component, signal, inject, PLATFORM_ID, ChangeDetectionStrategy } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Component({
    selector: 'app-dark-mode-toggle',
    templateUrl: './dark-mode-toggle.html',
    styleUrl: './dark-mode-toggle.css',
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DarkModeToggle {
    private platformId = inject(PLATFORM_ID);
    isDarkMode = signal(false);

    constructor() {
        if (isPlatformBrowser(this.platformId)) {
            const stored = localStorage.getItem('darkMode');

            if (stored) {
                if (stored === 'true') {
                    this.isDarkMode.set(true);
                } else {
                    this.isDarkMode.set(false);
                }
                this.applyDarkMode(this.isDarkMode());
            } else {
                // const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
                this.isDarkMode.set(true);
                this.applyDarkMode(this.isDarkMode());
            }
        }
    }

    toggleDarkMode(): void {
        const newValue = !this.isDarkMode();
        this.isDarkMode.set(newValue);
        this.applyDarkMode(newValue);
        localStorage.setItem('darkMode', String(newValue));
    }

    private applyDarkMode(isDark: boolean): void {
        if (isPlatformBrowser(this.platformId)) {
            document.documentElement.classList.toggle('dark', isDark);
        }
    }
}
