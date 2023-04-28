import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Subject} from "rxjs";
import {User} from "./user.model";

@Injectable({providedIn: 'root'})
export class AuthService {
  user = new Subject<User>();

  constructor(private http: HttpClient, private router: Router) {
  }

  signUp(email: string, password: string) {

    console.log(email);
    this.http.post<any>('http://localhost:8082/user/new', {email: email, password: password})
      .subscribe(
        response =>{
          this.user.next(new User(email, password))
          console.log(response);
          this.router.navigate(['travel']);
        },
        error => {
          console.log(error);
        }
      )
  }

  login(email: string, password: string) {

  }
}
