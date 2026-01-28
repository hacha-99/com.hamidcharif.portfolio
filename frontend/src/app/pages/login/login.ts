import { ChangeDetectionStrategy, Component, HostListener } from '@angular/core';
import { LoginCard } from '../../components/login-card/login-card';

@Component({
    selector: 'app-login',
    templateUrl: './login.html',
    styleUrl: './login.css',
    imports: [LoginCard],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class Login {
    isLoaded = false;

    @HostListener('window:load')
    onPageLoad() {
        this.isLoaded = true;
    }
}
