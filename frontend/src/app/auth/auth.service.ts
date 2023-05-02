import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Subject, tap} from "rxjs";
import {User} from "./user.model";

@Injectable({providedIn: 'root'})
export class AuthService {
  user = new Subject<User>();

  constructor(private http: HttpClient, private router: Router) {
  }

  signUp(email: string, password: string) {

    console.log(email);
    this.http.post<any>('http://localhost:8089/user/create', {email: email, password: password})
      .subscribe(
        response => {
          this.user.next(new User(response.email, response.password))
          this.router.navigate(['travel']);
        },
        error => {
          console.log(error);
        }
      )
  }

  login(email: string, password: string) {
    this.http.get<User>('http://localhost:8089/user/get-user/' + email)
      .subscribe(response => {
          if (password === response.password) {
            this.user.next(new User(response.email, response.password));
            this.router.navigate(['travel']);
          }
        },
        error => {
          console.log(error)
        })
  }

  logout() {
    this.user.next(null);
    this.router.navigate(['/auth']);
  }
}
