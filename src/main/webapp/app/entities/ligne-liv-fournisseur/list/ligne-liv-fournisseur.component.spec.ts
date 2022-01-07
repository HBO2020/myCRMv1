import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LigneLivFournisseurService } from '../service/ligne-liv-fournisseur.service';

import { LigneLivFournisseurComponent } from './ligne-liv-fournisseur.component';

describe('LigneLivFournisseur Management Component', () => {
  let comp: LigneLivFournisseurComponent;
  let fixture: ComponentFixture<LigneLivFournisseurComponent>;
  let service: LigneLivFournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LigneLivFournisseurComponent],
    })
      .overrideTemplate(LigneLivFournisseurComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LigneLivFournisseurComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LigneLivFournisseurService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.ligneLivFournisseurs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
