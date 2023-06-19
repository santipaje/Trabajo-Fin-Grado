import { Component } from '@angular/core';

interface MenuItem {
  name: string,
  route: string
}

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

public menulist: MenuItem[] = [
  { name: 'Feed', route:'feed'},
  { name: 'Search', route:'search'},
  { name: 'Hobbies', route:'hobbies'},
  { name: 'Liked', route:'liked'},
  { name: 'Profile', route:'profile'},
]

}
