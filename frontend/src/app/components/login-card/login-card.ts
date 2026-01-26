import { ChangeDetectionStrategy, Component, HostListener, inject, signal} from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
    selector: 'app-login-card',
    templateUrl: './login-card.html',
    styleUrl: './login-card.css',
    imports: [ReactiveFormsModule],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginCard {
    private readonly fb = inject(FormBuilder);

    protected readonly isLoading = signal(false);
    protected readonly errorMessage = signal<string | null>(null);

    protected readonly loginForm = this.fb.nonNullable.group({
        username: ['', [Validators.required, Validators.minLength(6)]],
        password: ['', [Validators.required, Validators.minLength(7)]],
    });

    protected onSubmit(): void {
        if (this.loginForm.invalid) {
            return;
        }

        this.isLoading.set(true);
        this.errorMessage.set(null);

        // TODO: Implement login logic with AuthService
        console.log('Login submitted:', this.loginForm.value);
    }

    @HostListener('focusin', ['$event'])
    onFocusIn(event: FocusEvent) {
        const target = event.target as HTMLElement;
        if (target?.tagName === 'INPUT') {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'center'
            });
        }
    }
}
