import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComandeListComponent } from './comande-list.component';

describe('ComandeListComponent', () => {
  let component: ComandeListComponent;
  let fixture: ComponentFixture<ComandeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComandeListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ComandeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
