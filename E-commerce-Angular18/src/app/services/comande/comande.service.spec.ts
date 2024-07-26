import { TestBed } from '@angular/core/testing';

import { ComandeService } from './comande.service';

describe('ComandeService', () => {
  let service: ComandeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComandeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
