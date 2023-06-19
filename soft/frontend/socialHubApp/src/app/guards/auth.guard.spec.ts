import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { guardsGuard } from './auth.guard';

describe('guardsGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => guardsGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
