import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { FeedComponent } from './pages/feed/feed.component';
import { SearchComponent } from './pages/search/search.component';
import { LikedComponent } from './pages/liked/liked.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { HobbiesComponent } from './pages/hobbies/hobbies.component';
import { UserprofileComponent } from './pages/userprofile/userprofile.component';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: 'feed', component: FeedComponent },
      { path: 'search', component: SearchComponent },
      { path: 'hobbies', component: HobbiesComponent },
      { path: 'liked', component: LikedComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'profile/:username', component: UserprofileComponent },
      { path: '**', redirectTo: 'feed'}
    ]
  },
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
