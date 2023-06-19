import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeRoutingModule } from './home-routing.module';
import { AppModule } from "../app.module";
import { FeedComponent } from './pages/feed/feed.component';
import { SearchComponent } from './pages/search/search.component';
import { HobbiesComponent } from './pages/hobbies/hobbies.component';
import { LikedComponent } from './pages/liked/liked.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HomeService } from '../services/home.service';
import { NavbarComponent } from '../shared/pages/navbar/navbar.component';
import { SharedModule } from '../shared/shared.module';
import { UserprofileComponent } from './pages/userprofile/userprofile.component';

@NgModule({
    declarations: [
        FeedComponent,
        SearchComponent,
        HobbiesComponent,
        LikedComponent,
        ProfileComponent,
        UserprofileComponent
    ],
    imports: [
        CommonModule,
        HomeRoutingModule,
        FormsModule,
        HttpClientModule,
        SharedModule
    ],
    providers:[
        HomeService
    ]
})
export class HomeModule { }
