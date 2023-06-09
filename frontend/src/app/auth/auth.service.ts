import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Subject, tap} from "rxjs";
import {User} from "./user.model";

@Injectable({providedIn: 'root'})
export class AuthService {
  public user = new Subject<User>();
  userId = '';

  constructor(private http: HttpClient, private router: Router) {
  }

  signUp(email: string, password: string) {

    console.log(email);
    this.http.post<any>('/api/user/create', {email: email, password: password})
      .subscribe(
        () => {
        }
      )
  }

  getUserId() {
    return this.userId;
  }

  login(email: string, password: string) {
    this.http.get<User>('/api/user/get-user/' + email)
      .subscribe(response => {
          if (password === response.password) {
            this.user.next(new User(response.id, response.email, response.password));
            this.userId = response.id;
            this.router.navigate(['travel']);
          }
        })
  }

  logout() {
    this.user.next(null);
    this.router.navigate(['/auth']);
  }
}
